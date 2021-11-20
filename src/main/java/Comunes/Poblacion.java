package Comunes;

import Individuos.IndividuoAckley;
import Individuos.IndividuoBeale;
import Individuos.IndividuoBukin;
import Individuos.IndividuoRastrigin;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Poblacion {
    int TAM_POBLACION;
    int NUM_GENERACIONES;
    double PROB_CRUCE;
    double PROB_MUTACION;
    int NUM_ELITISTAS;
    int TIPO_SELECCION_INDIVIUOS;
    int TIPO_POBLACION;
    int TIPO_CRUCE;
    int TIPO_MUTACION;
    boolean CRITERIO_ASCENDENTE;

    private Individuo[] poblacion;
    private double fitness_total;
    private Random rnd;

    private double[] fitnessInvertido;
    private double totalFitnessInvertido;


    public Poblacion(int TAM_POBLACION, int NUM_GENERACIONES, double PROB_CRUCE, double PROB_MUTACION, int NUM_ELITISTAS, int TIPO_POBLACION, int TIPO_SELECCION_INDIVIUOS, int TIPO_MUTACION, int TIPO_CRUCE) {
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

    public void generarPoblacion() {
        poblacion = new Individuo[TAM_POBLACION];
        for (int i = 0; i < poblacion.length; i++) {
            switch (TIPO_POBLACION) {
                case 0:
                    poblacion[i] = new IndividuoRastrigin(TIPO_MUTACION, TIPO_CRUCE);
                    break;
                case 1:
                    poblacion[i] = new IndividuoAckley(TIPO_MUTACION, TIPO_CRUCE);
                    break;
                case 2:
                    poblacion[i] = new IndividuoBeale(TIPO_MUTACION, TIPO_CRUCE);
                    break;
                case 3:
                    poblacion[i] = new IndividuoBukin(TIPO_MUTACION, TIPO_CRUCE);
                    break;
            }
        }

        this.evaluar();
    }

    public Individuo[] getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Individuo[] poblacion) {
        this.poblacion = poblacion;
        evaluar();
    }

    public double getFitness_total() {
        return fitness_total;
    }

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


    public Individuo getIndividuoConMejorFitnessEnPos(int posicion) {
        return poblacion[posicion];
    }

    //TODO Terminar seleccion individuos
    public Individuo seleccionIndividuos() {
        switch (TIPO_SELECCION_INDIVIUOS) {
            case 0: // Ruleta:
                return rouletteWheelSelection();

            case 1: // Método RANK

                break;

            case 2: // Método Tournament
                break;

            case 3: // Selección truncada
                break;

            default:
                return null;
        }
        return null;
    }


    public Individuo rouletteWheelSelection() {

        double randNum = rnd.nextDouble() * totalFitnessInvertido;
        int idx;
        for (idx = 0; idx < poblacion.length && randNum > 0; ++idx) {
            randNum -= fitnessInvertido[idx];
        }
        return poblacion[idx - 1];
    }

    public double getNextDouble() {
        return rnd.nextDouble();
    }

    @SuppressWarnings({"MethodDoesntCallSuperMethod", "CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    protected Poblacion clone(){
        return new Poblacion(TAM_POBLACION, NUM_GENERACIONES, PROB_CRUCE, PROB_MUTACION, NUM_ELITISTAS, TIPO_POBLACION, TIPO_SELECCION_INDIVIUOS, TIPO_MUTACION, TIPO_CRUCE);
    }
}
