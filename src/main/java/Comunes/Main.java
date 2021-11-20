package Comunes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    int TAM_POBLACION;
    int NUM_GENERACIONES;
    double PROB_CRUCE;
    double PROB_MUTACION;
    int NUM_ELITISTAS;

    int ESTRATEGIA_REEMPLAZAMIENTO;
    int TIPO_OPERADOR_CRUCE;
    int TIPO_POBLACION;
    int TIPO_OPERADOR_MUTACION;
    int TIPO_SELECCION_INDIVIUOS;

    {
        NUM_ELITISTAS = 20;
        TAM_POBLACION = 1000 + NUM_ELITISTAS;
        NUM_GENERACIONES = 5000;
        PROB_CRUCE = 0.7;
        PROB_MUTACION = 0.05;
        TIPO_POBLACION = 0;
        ESTRATEGIA_REEMPLAZAMIENTO = 0;
        TIPO_SELECCION_INDIVIUOS = 0;
        TIPO_OPERADOR_MUTACION = 2;
        TIPO_OPERADOR_CRUCE = 0;
    }


    public static void main(String[] args) {
        Main r = new Main();
        r.run();
    }

    public void run() {
        Poblacion poblacion = new Poblacion(TAM_POBLACION, NUM_GENERACIONES, PROB_CRUCE, PROB_MUTACION, NUM_ELITISTAS,
                TIPO_POBLACION, TIPO_SELECCION_INDIVIUOS, TIPO_OPERADOR_MUTACION, TIPO_OPERADOR_CRUCE);
        poblacion.generarPoblacion();
        Individuo[] nuevaPoblacion = new Individuo[TAM_POBLACION];
        Individuo[] individuos = new Individuo[2];

        System.out.println("Iteracion 0");
        System.out.println("Total Fitness = " + poblacion.getFitness_total());
        System.out.println("Mejor Fitness = "
                + poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness());
        System.out.print("Mejor Individuo: ");
        System.out.println(poblacion.getIndividuoConMejorFitnessEnPos(0).toString());

        int count;
        for (int num_iteracion = 1; num_iteracion <= NUM_GENERACIONES; num_iteracion++) {
            count = 0;

            for (int j = 0; j < NUM_ELITISTAS; ++j) {
                nuevaPoblacion[count] = poblacion.getIndividuoConMejorFitnessEnPos(j);
                count++;
            }

            while (count < TAM_POBLACION) {

                // Seleccion
                individuos[0] = poblacion.seleccionIndividuos();
                individuos[1] = poblacion.seleccionIndividuos();

                // Cruce
                if (poblacion.getNextDouble() < this.PROB_CRUCE) {
                    try {
                        individuos = individuos[0].cruzar(individuos[1]);
                    } catch (TipoCruceNoValidoException e) {
                        e.printStackTrace();
                    }
                }

                // Mutacion
                if (poblacion.getNextDouble() < PROB_MUTACION) {
                    individuos[0].mutar();
                }

                if (poblacion.getNextDouble() < PROB_MUTACION) {
                    individuos[1].mutar();
                }

                nuevaPoblacion[count] = individuos[0];
                nuevaPoblacion[count + 1] = individuos[1];
                count += 2;
            }

            if (ESTRATEGIA_REEMPLAZAMIENTO == 0) {
                // Esquema generacional
                poblacion.setPoblacion(nuevaPoblacion.clone());
            } else {
                List<Individuo> mezcla = new ArrayList<>();
                Poblacion nueva = poblacion.clone();
                mezcla.addAll(Arrays.asList(poblacion.getPoblacion()));
                mezcla.addAll(Arrays.asList(nueva.getPoblacion()));
                mezcla = mezcla.stream()
                        .parallel()
                        .sorted(Individuo::compareTo)
                        .distinct()
                        .limit(TAM_POBLACION)
                        .collect(Collectors.toList());

                poblacion.setPoblacion(mezcla.toArray(new Individuo[0]));

            }

            if (num_iteracion % (NUM_GENERACIONES / 10) == 0) {

                System.out.println("******************************************************************" +
                        "*****************************************************************************");
                System.out.println("Iteracion: " + num_iteracion);
                System.out.println("Total Fitness = " + poblacion.getFitness_total());
                System.out.println("Mejor Fitness = "
                        + poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness());
                System.out.print("Mejor Individuo: ");
                System.out.println(poblacion.getIndividuoConMejorFitnessEnPos(0).toString());

            }

        }

    }

}
