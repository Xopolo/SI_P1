package Modelo.Comunes;

import Modelo.Comunes.Enums.*;

/**
 * Clase parametros que almacena los parametros de ejecucion
 */
public class Parametros {

    private int TAM_POBLACION;
    private int NUM_GENERACIONES;
    private double PROB_CRUCE;
    private double PROB_MUTACION;
    private int NUM_ELITISTAS;
    private boolean EXPORTAR_A_CSV;

    private Est_reemplazamiento ESTRATEGIA_REEMPLAZAMIENTO;
    private Cruce TIPO_OPERADOR_CRUCE;
    private Poblaciones TIPO_POBLACION;
    private Mutacion TIPO_OPERADOR_MUTACION;
    private SeleccionIndividuos TIPO_SELECCION_INDIVIUOS;

    /**
     * Constructor por defecto de la clase Parametros
     */
    public Parametros() {
        NUM_ELITISTAS = 5;
        TAM_POBLACION = 1000 + NUM_ELITISTAS;
        NUM_GENERACIONES = 50000;
        PROB_CRUCE = 0.7;
        PROB_MUTACION = 0.05;
        TIPO_POBLACION = Poblaciones.RASTRIGIN;
        ESTRATEGIA_REEMPLAZAMIENTO = Est_reemplazamiento.GENERACIONAL;
        TIPO_SELECCION_INDIVIUOS = SeleccionIndividuos.RULETA;
        TIPO_OPERADOR_MUTACION = Mutacion.NORMAL;
        TIPO_OPERADOR_CRUCE = Cruce.UNIFORME;
        EXPORTAR_A_CSV = true;
    }

    /**
     * Constructor de la clase Parametros
     *
     * @param args the args Los argumentos de ejecucion
     */
    public Parametros(String[] args) {
        this();
        double var;
        if (args.length != 0) System.out.println("Leyendo parametros...");

        for (int i = 0; i < args.length; i += 2) {
            if (args.length - 1 < i + 1) continue;

            try {
                var = Double.parseDouble(args[i + 1]);
            } catch (NumberFormatException e) {
                System.out.println(String.format("Valor de parametro: ", args[i + 1], "invalido"));
                continue;
            }

            switch (args[i]) {
                case "-tam_pob":
                    TAM_POBLACION = (int) (var + NUM_ELITISTAS);
                    break;
                case "-n_elit":
                    TAM_POBLACION -= NUM_ELITISTAS;
                    NUM_ELITISTAS = (int) var;
                    TAM_POBLACION += NUM_ELITISTAS;
                    break;
                case "-n_gen":
                    NUM_GENERACIONES = (int) var;
                    break;
                case "-pb_cruce":
                    PROB_CRUCE = var;
                    break;
                case "-pb_mutacion":
                    PROB_MUTACION = var;
                    break;
                case "-tp_pob":
                    TIPO_POBLACION =
                            ((int) var == 0) ? Poblaciones.RASTRIGIN :
                                    ((int) var == 1) ? Poblaciones.ACKLEY :
                                            ((int) var == 2) ? Poblaciones.BEALE :
                                                    Poblaciones.BUKIN;
                    break;
                case "-est_reemp":
                    ESTRATEGIA_REEMPLAZAMIENTO = ((int) var == 0) ? Est_reemplazamiento.ESTACIONARIA : Est_reemplazamiento.GENERACIONAL;
                    break;
                case "-tp_sel":
                    TIPO_SELECCION_INDIVIUOS =
                            ((int) var == 0) ? SeleccionIndividuos.RULETA :
                                    ((int) var == 1) ? SeleccionIndividuos.RANK :
                                            ((int) var == 2) ? SeleccionIndividuos.TORNEO :
                                                    SeleccionIndividuos.TRUNCADA;
                    break;
                case "-tp_op_mut":
                    TIPO_OPERADOR_MUTACION =
                            ((int) var == 0) ? Mutacion.UNIFORME :
                                    ((int) var == 1) ? Mutacion.NORMAL :
                                            Mutacion.ALEATORIO;
                    break;
                case "-tp_op_cru":
                    TIPO_OPERADOR_CRUCE =
                            ((int) var == 0) ? Cruce.PUNTO :
                                    ((int) var == 1) ? Cruce.MULTIPUNTO :
                                            Cruce.UNIFORME;
                    break;
                case "-csv":
                    EXPORTAR_A_CSV = var != 0;
                    break;
                default:
                    System.out.println("Comando incorrecto: " + args[i]);
                    break;
            }
        }
        if (args.length != 0) System.out.println("Parametros establecidos");
    }

    /**
     * Gets tam poblacion.
     *
     * @return the tam poblacion
     */
    public int getTAM_POBLACION() {
        return TAM_POBLACION;
    }

    /**
     * Sets tam poblacion.
     *
     * @param TAM_POBLACION the tam poblacion
     */
    public void setTAM_POBLACION(int TAM_POBLACION) {
        this.TAM_POBLACION = TAM_POBLACION;
    }

    /**
     * Gets num generaciones.
     *
     * @return the num generaciones
     */
    public int getNUM_GENERACIONES() {
        return NUM_GENERACIONES;
    }

    /**
     * Sets num generaciones.
     *
     * @param NUM_GENERACIONES the num generaciones
     */
    public void setNUM_GENERACIONES(int NUM_GENERACIONES) {
        this.NUM_GENERACIONES = NUM_GENERACIONES;
    }

    /**
     * Gets prob cruce.
     *
     * @return the prob cruce
     */
    public double getPROB_CRUCE() {
        return PROB_CRUCE;
    }

    /**
     * Sets prob cruce.
     *
     * @param PROB_CRUCE the prob cruce
     */
    public void setPROB_CRUCE(double PROB_CRUCE) {
        this.PROB_CRUCE = PROB_CRUCE;
    }

    /**
     * Gets prob mutacion.
     *
     * @return the prob mutacion
     */
    public double getPROB_MUTACION() {
        return PROB_MUTACION;
    }

    /**
     * Sets prob mutacion.
     *
     * @param PROB_MUTACION the prob mutacion
     */
    public void setPROB_MUTACION(double PROB_MUTACION) {
        this.PROB_MUTACION = PROB_MUTACION;
    }

    /**
     * Gets num elitistas.
     *
     * @return the num elitistas
     */
    public int getNUM_ELITISTAS() {
        return NUM_ELITISTAS;
    }

    /**
     * Sets num elitistas.
     *
     * @param NUM_ELITISTAS the num elitistas
     */
    public void setNUM_ELITISTAS(int NUM_ELITISTAS) {
        this.NUM_ELITISTAS = NUM_ELITISTAS;
    }

    /**
     * Is exportar a csv boolean.
     *
     * @return the boolean
     */
    public boolean isEXPORTAR_A_CSV() {
        return EXPORTAR_A_CSV;
    }

    /**
     * Sets exportar a csv.
     *
     * @param EXPORTAR_A_CSV the exportar a csv
     */
    public void setEXPORTAR_A_CSV(boolean EXPORTAR_A_CSV) {
        this.EXPORTAR_A_CSV = EXPORTAR_A_CSV;
    }

    /**
     * Gets estrategia reemplazamiento.
     *
     * @return the estrategia reemplazamiento
     */
    public Est_reemplazamiento getESTRATEGIA_REEMPLAZAMIENTO() {
        return ESTRATEGIA_REEMPLAZAMIENTO;
    }

    /**
     * Sets estrategia reemplazamiento.
     *
     * @param ESTRATEGIA_REEMPLAZAMIENTO the estrategia reemplazamiento
     */
    public void setESTRATEGIA_REEMPLAZAMIENTO(Est_reemplazamiento ESTRATEGIA_REEMPLAZAMIENTO) {
        this.ESTRATEGIA_REEMPLAZAMIENTO = ESTRATEGIA_REEMPLAZAMIENTO;
    }

    /**
     * Gets tipo operador cruce.
     *
     * @return the tipo operador cruce
     */
    public Cruce getTIPO_OPERADOR_CRUCE() {
        return TIPO_OPERADOR_CRUCE;
    }

    /**
     * Sets tipo operador cruce.
     *
     * @param TIPO_OPERADOR_CRUCE the tipo operador cruce
     */
    public void setTIPO_OPERADOR_CRUCE(Cruce TIPO_OPERADOR_CRUCE) {
        this.TIPO_OPERADOR_CRUCE = TIPO_OPERADOR_CRUCE;
    }

    /**
     * Gets tipo poblacion.
     *
     * @return the tipo poblacion
     */
    public Poblaciones getTIPO_POBLACION() {
        return TIPO_POBLACION;
    }

    /**
     * Sets tipo poblacion.
     *
     * @param TIPO_POBLACION the tipo poblacion
     */
    public void setTIPO_POBLACION(Poblaciones TIPO_POBLACION) {
        this.TIPO_POBLACION = TIPO_POBLACION;
    }

    /**
     * Gets tipo operador mutacion.
     *
     * @return the tipo operador mutacion
     */
    public Mutacion getTIPO_OPERADOR_MUTACION() {
        return TIPO_OPERADOR_MUTACION;
    }

    /**
     * Sets tipo operador mutacion.
     *
     * @param TIPO_OPERADOR_MUTACION the tipo operador mutacion
     */
    public void setTIPO_OPERADOR_MUTACION(Mutacion TIPO_OPERADOR_MUTACION) {
        this.TIPO_OPERADOR_MUTACION = TIPO_OPERADOR_MUTACION;
    }

    /**
     * Gets tipo seleccion indiviuos.
     *
     * @return the tipo seleccion indiviuos
     */
    public SeleccionIndividuos getTIPO_SELECCION_INDIVIUOS() {
        return TIPO_SELECCION_INDIVIUOS;
    }

    /**
     * Sets tipo seleccion indiviuos.
     *
     * @param TIPO_SELECCION_INDIVIUOS the tipo seleccion indiviuos
     */
    public void setTIPO_SELECCION_INDIVIUOS(SeleccionIndividuos TIPO_SELECCION_INDIVIUOS) {
        this.TIPO_SELECCION_INDIVIUOS = TIPO_SELECCION_INDIVIUOS;
    }
}
