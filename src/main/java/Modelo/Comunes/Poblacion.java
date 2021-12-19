package Modelo.Comunes;

import Modelo.Comunes.Enums.Cruce;
import Modelo.Comunes.Enums.Mutacion;
import Modelo.Comunes.Enums.Poblaciones;
import Modelo.Comunes.Enums.SeleccionIndividuos;
import Modelo.Individuos.IndividuoAckley;
import Modelo.Individuos.IndividuoBeale;
import Modelo.Individuos.IndividuoBukin;
import Modelo.Individuos.IndividuoRastrigin;

import java.util.Arrays;
import java.util.Random;

/**
 * La clase Poblacion
 *
 * Contiene muchos individuos
 */
public class Poblacion {
    /**
     * El tamaño de la poblacion
     */
    int TAM_POBLACION;
    /**
     * El Numero de generaciones.
     */
    int NUM_GENERACIONES;
    /**
     * La Prob de cruce.
     */
    double PROB_CRUCE;
    /**
     * La Prob de mutacion.
     */
    double PROB_MUTACION;
    /**
     * El Numero elitistas.
     */
    int NUM_ELITISTAS;

    private Cruce TIPO_CRUCE;
    private Poblaciones TIPO_POBLACION;
    private Mutacion TIPO_MUTACION;
    private SeleccionIndividuos TIPO_SELECCION_INDIVIUOS;

    private Individuo[] poblacion;
    private double fitness_total;
    private Random rnd;

    private double[] fitnessInvertido;
    private double totalFitnessInvertido;


    /**
     * Constructor de la clase Poblacion.
     *
     * @param TAM_POBLACION            the tam poblacion
     * @param NUM_GENERACIONES         the num generaciones
     * @param PROB_CRUCE               the prob cruce
     * @param PROB_MUTACION            the prob mutacion
     * @param NUM_ELITISTAS            the num elitistas
     * @param TIPO_POBLACION           the tipo poblacion
     * @param TIPO_SELECCION_INDIVIUOS the tipo seleccion indiviuos
     * @param TIPO_MUTACION            the tipo mutacion
     * @param TIPO_CRUCE               the tipo cruce
     */
    public Poblacion(int TAM_POBLACION, int NUM_GENERACIONES, double PROB_CRUCE, double PROB_MUTACION, int NUM_ELITISTAS,
                     Poblaciones TIPO_POBLACION, SeleccionIndividuos TIPO_SELECCION_INDIVIUOS, Mutacion TIPO_MUTACION, Cruce TIPO_CRUCE) {
        this.TIPO_CRUCE = TIPO_CRUCE;
        this.TIPO_MUTACION = TIPO_MUTACION;
        this.TAM_POBLACION = TAM_POBLACION;
        this.TIPO_POBLACION = TIPO_POBLACION;
        this.NUM_GENERACIONES = NUM_GENERACIONES;
        this.PROB_CRUCE = PROB_CRUCE;
        this.PROB_MUTACION = PROB_MUTACION;
        this.NUM_ELITISTAS = NUM_ELITISTAS;
        this.TIPO_SELECCION_INDIVIUOS = TIPO_SELECCION_INDIVIUOS;
        rnd = new Random(System.nanoTime());
    }

    /**
     * Instantiates a new Poblacion.
     *
     * @param parametros the parametros
     */
    public Poblacion(Parametros parametros) {
        this.TIPO_CRUCE = parametros.getTIPO_OPERADOR_CRUCE();
        this.TIPO_MUTACION = parametros.getTIPO_OPERADOR_MUTACION();
        this.TAM_POBLACION = parametros.getTAM_POBLACION();
        this.TIPO_POBLACION = parametros.getTIPO_POBLACION();
        this.NUM_GENERACIONES = parametros.getNUM_GENERACIONES();
        this.PROB_CRUCE = parametros.getPROB_CRUCE();
        this.PROB_MUTACION = parametros.getPROB_MUTACION();
        this.NUM_ELITISTAS = parametros.getNUM_ELITISTAS();
        this.TIPO_SELECCION_INDIVIUOS = parametros.getTIPO_SELECCION_INDIVIUOS();
        rnd = new Random(System.nanoTime());
    }

    /**
     * Generar poblacion.
     */
    public void generarPoblacion() {
        poblacion = new Individuo[TAM_POBLACION];
        for (int i = 0; i < poblacion.length; i++) {
            switch (TIPO_POBLACION) {
                case RASTRIGIN:
                    poblacion[i] = new IndividuoRastrigin(TIPO_MUTACION, TIPO_CRUCE);
                    break;
                case ACKLEY:
                    poblacion[i] = new IndividuoAckley(TIPO_MUTACION, TIPO_CRUCE);
                    break;
                case BEALE:
                    poblacion[i] = new IndividuoBeale(TIPO_MUTACION, TIPO_CRUCE);
                    break;
                case BUKIN:
                    poblacion[i] = new IndividuoBukin(TIPO_MUTACION, TIPO_CRUCE);
                    break;
            }
        }

        this.evaluar();
    }

    /**
     * Get poblacion individuo [ ].
     *
     * @return the individuo [ ]
     */
    public Individuo[] getPoblacion() {
        return poblacion;
    }

    /**
     * Sets poblacion.
     *
     * @param poblacion the poblacion
     */
    public void setPoblacion(Individuo[] poblacion) {
        this.poblacion = poblacion;
        evaluar();
    }

    /**
     * Gets fitness total.
     *
     * @return the fitness total
     */
    public double getFitness_total() {
        return fitness_total;
    }

    /**
     * Metodo que evalua todos los individuos de la poblacion
     *
     * @return El fitness total de la poblacion
     */
    public double evaluar() {
        fitness_total = 0;
        totalFitnessInvertido = 0;
        fitnessInvertido = new double[poblacion.length];

        for (Individuo ind : poblacion
        ) {
            fitness_total += ind.evaluar();
        }

        poblacion = Arrays.stream(poblacion)
                .parallel()
                .sorted(Individuo::compareTo).toArray(Individuo[]::new);

        for (int i = 0; i < poblacion.length; i++) {
            fitnessInvertido[i] = fitness_total / poblacion[i].getFitness();
            totalFitnessInvertido += fitnessInvertido[i];
        }

        return fitness_total;
    }


    /**
     * Devuelve el individuo (segun el fitness y el parametro) de los mejores individuos de la poblacion
     *
     * @param posicion La posicion a consultar
     * @return the individuo con mejor fitness en pos
     */
    public Individuo getIndividuoConMejorFitnessEnPos(int posicion) {
        return poblacion[posicion];
    }

    /**
     * Metodo de seleccion de individuos
     *
     * @return Un individuo aleatorio de la poblacion
     */
    public Individuo seleccionIndividuos() {
        switch (TIPO_SELECCION_INDIVIUOS) {
            case RULETA: // Ruleta:
                return rouletteWheelSelection();

            case RANK: // Método RANK
                return rankSelection();

            case TORNEO: // Método Tournament
                return tournamentSelection();

            case TRUNCADA: // Selección truncada
                return truncatedSelection();

            default:
                return null;
        }
    }

    private Individuo rouletteWheelSelection() {

        double randNum = rnd.nextDouble() * totalFitnessInvertido;
        int idx;
        for (idx = 0; idx < poblacion.length && randNum > 0; ++idx) {
            randNum -= fitnessInvertido[idx];
        }
        return poblacion[idx - 1];
    }

    private Individuo rankSelection() {
        double randNum = rnd.nextDouble() * totalFitnessInvertido;
        int idx;
        for (idx = poblacion.length - 1; idx >= 0 && randNum > 0; --idx) {
            randNum -= fitnessInvertido[idx];
        }
        return poblacion[idx + 1];
    }

    private Individuo tournamentSelection() {
        int k = 2;
        Individuo[] individuos = new Individuo[k * 2];
        for (int i = 0; i < individuos.length; i++) {
            individuos[i] = poblacion[rnd.nextInt(poblacion.length)];
        }
        Arrays.sort(individuos, Individuo::compareTo);
        return individuos[0];
    }

    private Individuo truncatedSelection() {
        int tam = (int) (TAM_POBLACION * 0.7d);
        return poblacion[rnd.nextInt(tam)];
    }

    /**
     * Devuelve un double aleatorio
     *
     * @return the next double
     */
    public double getNextDouble() {
        return rnd.nextDouble();
    }

    @SuppressWarnings({"MethodDoesntCallSuperMethod", "CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    protected Poblacion clone() {
        return new Poblacion(TAM_POBLACION, NUM_GENERACIONES, PROB_CRUCE, PROB_MUTACION, NUM_ELITISTAS, TIPO_POBLACION, TIPO_SELECCION_INDIVIUOS, TIPO_MUTACION, TIPO_CRUCE);
    }
}
