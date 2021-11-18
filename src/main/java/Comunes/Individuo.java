package Comunes;

import java.util.Random;

public abstract class Individuo {

    protected Double fitness;
    protected final int tipo_mutacion;
    protected final int tipo_cruce;
    protected double[] coords;
    protected double limite;
    protected boolean valido;
    protected int N;
    private Random rnd;


    public Individuo(int tipo_mutacion, int tipo_cruce) {
        rnd = new Random(System.nanoTime());
        valido = true;
        this.tipo_mutacion = tipo_mutacion;
        this.tipo_cruce = tipo_cruce;
    }

    public double getFitness() {
        return fitness;
    }

    public abstract double evaluar();

    public boolean isValido() {
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < -limite || coords[i] > limite) {
                valido = false;
            }
        }

        return valido;
    }

    public void mutar() {
        Random rnd = new Random();
        int coord_i = rnd.nextInt(coords.length);
        switch (tipo_mutacion) {
            case 0:
                coords[coord_i] += (rnd.nextDouble() * 2) - 1;
                break;
            case 1:
                double std_deviation = 1; // Valores por defecto en distribución normal
                double media = 0;
                coords[coord_i] += rnd.nextGaussian() * std_deviation + media;
                break;
            case 2:
                coords[coord_i] = ((rnd.nextDouble() * 2) - 1) * limite;
                break;
        }
        isValido();
    }


    public Individuo[] cruzar(Individuo individuo2) throws TipoCruceNoValidoException {
        if(this.getClass() != individuo2.getClass()) return null;
        switch (tipo_cruce) {
            case 0: // Cruce en un punto
                return cruceEnPunto(individuo2);
            case 1: // Cruce uniforme:
                return cruceUniforme(individuo2);
            case 2:
                if (N < 3) {
                    return cruceMultipunto(individuo2);
                }
                throw new TipoCruceNoValidoException();
            default:
                throw new TipoCruceNoValidoException();
        }

    }
    //TODO Cruces


    private Individuo[] cruceEnPunto(Individuo individuo2) {
        int cambio = rnd.nextInt(this.coords.length);
        Individuo nuevo = this.getNewIndividuo();
        // Cambia de coordenada de individuo a partir del cambio
    }

    private Individuo[] cruceUniforme(Individuo individuo2) {
        // Se hace un random de que coordenada coger para cada posicion
    }

    private Individuo[] cruceMultipunto(Individuo individuo2) {
        // Como el cruce en un punto PERO con dos puntos
    }

    protected abstract Individuo getNewIndividuo();
}
