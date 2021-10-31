package Comunes;

import Individuos.IndividuoRastrigin;

public class Poblacion {
    int TAM_POBLACION;
    int NUM_GENERACIONES;
    double PROB_CRUCE;
    double PROB_MUTACION;
    int NUM_ELITISTAS;
    int TIPO_SELECCION_INDIVIUOS;

    private Individuo[] poblacion;
    private double fitness_total;
    private boolean evaluado;
    private ColaMejoresIndividuos colaMejoresIndividuos;

    public Poblacion(int TAM_POBLACION, int NUM_GENERACIONES, double PROB_CRUCE, double PROB_MUTACION, int NUM_ELITISTAS, int TIPO_SELECCION_INDIVIUOS) {
        this.TAM_POBLACION = TAM_POBLACION;
        this.NUM_GENERACIONES = NUM_GENERACIONES;
        this.PROB_CRUCE = PROB_CRUCE;
        this.PROB_MUTACION = PROB_MUTACION;
        this.NUM_ELITISTAS = NUM_ELITISTAS;
        this.TIPO_SELECCION_INDIVIUOS = TIPO_SELECCION_INDIVIUOS;
    }

    public void generarPoblacion(int tipo_mutacion) {
        poblacion = new Individuo[TAM_POBLACION];
        for (int i = 0; i < TAM_POBLACION; i++) {
            poblacion[i] = new IndividuoRastrigin(tipo_mutacion);
        }

        this.evaluar();
        evaluado = true;
    }

    public Individuo[] getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Individuo[] poblacion) {
        this.poblacion = poblacion;
    }

    public double getFitness_total() {
        return fitness_total;
    }

    public void setFitness_total(double fitness_total) {
        this.fitness_total = fitness_total;
    }

    public boolean isEvaluado() {
        return evaluado;
    }

    public void setEvaluado(boolean evaluado) {
        this.evaluado = evaluado;
    }

    public double evaluar() {
        fitness_total = 0;
        colaMejoresIndividuos = null;

        for (Individuo ind : poblacion
        ) {
            fitness_total += ind.evaluar();
        }

        colaMejoresIndividuos = new ColaMejoresIndividuos(NUM_ELITISTAS);
        for (int i = 0; i < TAM_POBLACION; i++) { // Para almacenar los primeros
            colaMejoresIndividuos.add(poblacion[i]);
        }

        return fitness_total;
    }


    public Individuo getIndividuoConMejorFitnessEnPos(int posicion) {
        if (!evaluado) {
            evaluar();
        }
        return colaMejoresIndividuos.get(posicion);
    }

    public Individuo seleccionIndividuos() {
        switch (TIPO_SELECCION_INDIVIUOS) {
            case 0: // Ruleta:
                break;

            case 1: // Método RANK
                break;

            case 2: // Método Tournament
                break;

            case 3: // Selección truncada
                break;

            default:
                return null;
        }
    }


}
