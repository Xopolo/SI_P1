package Modelo.Comunes;

import Modelo.Comunes.Enums.Est_reemplazamiento;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase ejecutar que contiene el algoritmo genético
 */
public class Ejecutar {

    private static final char SEPARADOR = ';';
    private Parametros parametros;
    private File file_csv_general;
    private boolean salida_por_consola;
    private TextArea salida;
    private LineChart grafico;
    private XYChart.Series<String, Number> fitness_mejor;
    private XYChart.Series<String, Number> fitness_total;

    /**
     * Constructor de la clase ejecutar
     *
     * @param parametros Los parametros de ejecucion
     * @param salida     El TextArea por el que se produce la salida
     * @param grafico    El grafico donde se pinta la gráfica
     */
    public Ejecutar(Parametros parametros, TextArea salida, LineChart<String, Number> grafico) {
        this.parametros = parametros;
        this.salida_por_consola = false;
        this.salida = salida;
        this.grafico = grafico;
        this.fitness_mejor = new XYChart.Series<>();
        this.fitness_total = new XYChart.Series<>();
    }

    /**
     * Constructor por defecto de la clase Ejecutar
     */
    public Ejecutar() {

    }

    /**
     * The entry point of application.
     *
     * @param args Los parametros de ejecucion
     */
    public static void main(String[] args) {
        Ejecutar ejecutar = new Ejecutar();
        ejecutar.run(new Parametros(args));
    }

    /**
     * Metodo que saca la ejecucion de un contexto estático
     *
     * @param parametros Los parametros de ejecucion
     */
    public void run(Parametros parametros) {
        this.parametros = parametros;
        salida_por_consola = true;
        ejecutar();
    }

    /**
     * Ejecuta el algoritmo genético
     */
    public void ejecutar() {
        Poblacion poblacion = new Poblacion(parametros);
        poblacion.generarPoblacion();
        Individuo[] nuevaPoblacion = new Individuo[parametros.getTAM_POBLACION()];
        Individuo[] individuos = new Individuo[2];
        Writer csv_general = null;
        Writer csv_ejecucion = null;
        if (parametros.isEXPORTAR_A_CSV()) {
            csv_general = prepararFileGeneral(parametros);
            csv_ejecucion = prepararFile();
        }

        imprimir("Iteracion 0\n");
        imprimir("Total Fitness = " + poblacion.getFitness_total() + "\n");
        imprimir("Mejor Fitness = "
                + poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness() + "\n");
        imprimir("Mejor Individuo: ");
        imprimir(poblacion.getIndividuoConMejorFitnessEnPos(0).toString() + "\n");

        if (!salida_por_consola) {

            fitness_mejor.getData().add(new XYChart.Data("0", poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness()));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    grafico.getData().add(fitness_mejor);
                }
            });
        }


        int count;
        for (int num_iteracion = 1; num_iteracion <= parametros.getNUM_GENERACIONES(); num_iteracion++) {
            count = 0;

            for (int j = 0; j < parametros.getNUM_ELITISTAS(); ++j) {
                nuevaPoblacion[count] = poblacion.getIndividuoConMejorFitnessEnPos(j);
                count++;
            }

            while (count < parametros.getTAM_POBLACION()) {

                // Seleccion
                individuos[0] = poblacion.seleccionIndividuos();
                do {
                    individuos[1] = poblacion.seleccionIndividuos();
                } while (individuos[0].equals(individuos[1]));

                // Cruce
                if (poblacion.getNextDouble() < parametros.getPROB_CRUCE()) {

                    try {
                        individuos = individuos[0].cruzar(individuos[1]);
                    } catch (TipoCruceNoValidoException e) {
                        e.printStackTrace();
                    }

                    // Mutacion
                    if (poblacion.getNextDouble() < parametros.getPROB_MUTACION()) {
                        individuos[0].mutar();
                    }

                    if (poblacion.getNextDouble() < parametros.getPROB_MUTACION()) {
                        individuos[1].mutar();
                    }
                    nuevaPoblacion[count] = individuos[0];
                    nuevaPoblacion[count + 1] = individuos[1];
                    count += 2;
                }
            }

            if (parametros.getESTRATEGIA_REEMPLAZAMIENTO() == Est_reemplazamiento.GENERACIONAL) {
                // Esquema generacional
                poblacion.setPoblacion(nuevaPoblacion.clone());
            } else {
                List<Individuo> mezcla = new ArrayList<>();
                Poblacion nueva = poblacion.clone();
                nueva.setPoblacion(nuevaPoblacion);
                mezcla.addAll(Arrays.asList(poblacion.getPoblacion()));
                mezcla.addAll(Arrays.asList(nueva.getPoblacion()));
                mezcla = mezcla.stream()
                        .sequential()
                        .sorted(Individuo::compareTo)
                        .distinct()
                        .limit(parametros.getTAM_POBLACION())
                        .collect(Collectors.toList());
                poblacion.setPoblacion(mezcla.toArray(new Individuo[0]));

            }

            Individuo mejor_de_iteracion = poblacion.getIndividuoConMejorFitnessEnPos(0);
            if (parametros.isEXPORTAR_A_CSV()) {
                try {
                    csv_ejecucion.write(mejor_de_iteracion.getFitness() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (num_iteracion % (parametros.getNUM_GENERACIONES() / 50) == 0) {

                imprimir("******************************************************************" +
                        "*****************************************************************************\n");
                imprimir("Iteracion: " + num_iteracion + "\n");
                imprimir("Total Fitness = " + poblacion.getFitness_total() + "\n");
                imprimir("Mejor Fitness = "
                        + mejor_de_iteracion.getFitness() + "\n");
                imprimir("Mejor Individuo: ");
                imprimir(mejor_de_iteracion + "\n");

                if (!salida_por_consola) {
                    String st_num_iter = String.valueOf(num_iteracion);
                    Platform.runLater(() -> {
                        fitness_mejor.getData().add(new XYChart.Data<>(st_num_iter, poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness()));
//                    fitness_total.getData().add(new XYChart.Data<>(String.valueOf(num_iteracion), poblacion.getFitness_total()));
                    });

                }

            }

        }
        if (!salida_por_consola) {
            this.grafico.autosize();
            this.grafico.getXAxis().requestLayout();
        }
        if (csv_general != null) {
            try {
                Individuo mejor = poblacion.getIndividuoConMejorFitnessEnPos(0);
                csv_general.write((String.valueOf(mejor.getFitness()) + SEPARADOR));
                csv_general.write((Arrays.toString(mejor.getCoords()) + SEPARADOR));
                csv_general.write(file_csv_general.getName());
                csv_general.write("\n");
                csv_general.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (csv_ejecucion != null) {
            try {
                csv_general.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Writer prepararFileGeneral(Parametros parametros) {
        File file = new File("Resultados\\ResultadosGenerales.csv");

        BufferedWriter bufferedWriter = null;
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                bufferedWriter = new BufferedWriter(osw);
                insertarCabeceras(bufferedWriter);
                insertarValoresGenerales(bufferedWriter, parametros);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(file, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                bufferedWriter = new BufferedWriter(osw);
                insertarValoresGenerales(bufferedWriter, parametros);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedWriter;
    }

    private Writer prepararFile() {
        file_csv_general = new File("Resultados\\Ejecuciones\\" + LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".csv");

        BufferedWriter bufferedWriter = null;
        try {
            file_csv_general.getParentFile().mkdirs();
            file_csv_general.createNewFile();
            FileOutputStream fos = new FileOutputStream(file_csv_general);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(osw);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }

    private void insertarValoresGenerales(BufferedWriter bufferedWriter, Parametros parametros) {
        StringBuilder sb = new StringBuilder();
        sb.append(parametros.getTAM_POBLACION()).append(SEPARADOR);
        sb.append(parametros.getNUM_GENERACIONES()).append(SEPARADOR);
        sb.append(parametros.getNUM_ELITISTAS()).append(SEPARADOR);
        sb.append(parametros.getPROB_MUTACION()).append(SEPARADOR);
        sb.append(parametros.getPROB_CRUCE()).append(SEPARADOR);
        sb.append(parametros.getTIPO_POBLACION()).append(SEPARADOR);
        sb.append(parametros.getESTRATEGIA_REEMPLAZAMIENTO()).append(SEPARADOR);
        sb.append(parametros.getTIPO_SELECCION_INDIVIUOS()).append(SEPARADOR);
        sb.append(parametros.getTIPO_OPERADOR_MUTACION()).append(SEPARADOR);
        sb.append(parametros.getTIPO_OPERADOR_CRUCE()).append(SEPARADOR);
        try {
            bufferedWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertarCabeceras(BufferedWriter bufferedWriter) {
        StringBuilder sb = new StringBuilder();
        sb.append("TAMANIO POBLACION").append(SEPARADOR);
        sb.append("NUM GENERACIONES").append(SEPARADOR);
        sb.append("NUM ELITISTAS").append(SEPARADOR);
        sb.append("PROB MUTACION").append(SEPARADOR);
        sb.append("PROB CRUCE").append(SEPARADOR);
        sb.append("TIPO POBLACION").append(SEPARADOR);
        sb.append("REMPLAZAMIENTO").append(SEPARADOR);
        sb.append("SELECCION INDIVIDUOS").append(SEPARADOR);
        sb.append("TIPO MUTACION").append(SEPARADOR);
        sb.append("TIPO CRUCE").append(SEPARADOR);
        sb.append("MEJOR FITNESS").append(SEPARADOR);
        sb.append("COORDENADAS").append(SEPARADOR);
        sb.append("NOMBRE ARCHIVO EJECUCION").append("\n");
        try {
            bufferedWriter.append(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void imprimir(String cadena) {
        if (salida_por_consola) {
            System.out.print(cadena);
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
//                    salida.getText()
                    salida.appendText(cadena);
                }
            });

        }
    }

}
