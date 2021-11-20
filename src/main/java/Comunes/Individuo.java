package Comunes;

import java.util.Arrays;
import java.util.Random;

public abstract class Individuo implements Comparable<Individuo> {

    protected Double fitness;
    protected final int tipo_mutacion;
    protected final int tipo_cruce;
    protected double[] coords;
    protected double limite;
    protected boolean valido;
    protected int N;
    protected Random rnd;

    public Individuo(int tipo_mutacion, int tipo_cruce) {

        rnd = new Random(System.nanoTime());
        valido = true;
        this.tipo_mutacion = tipo_mutacion;
        this.tipo_cruce = tipo_cruce;
    }

    public double getFitness() {
        return fitness;
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    protected abstract Individuo getNewIndividuo();

    public boolean isValido() {
        for (double coord : coords) {
            if (coord < -limite || coord > limite) {
                valido = false;
                break;
            }
        }

        return valido;
    }

    public abstract double evaluar();

    public void mutar() {
        Random rnd = new Random();
        int coord_i = rnd.nextInt(coords.length);
        switch (tipo_mutacion) {
            case 0: //
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

        if (this.getClass() != individuo2.getClass()) return null;
        switch (tipo_cruce) {
            case 0: // Cruce en un punto
                return cruceEnPunto(individuo2);
            case 1: // Cruce uniforme:
                return cruceUniforme(individuo2);
            case 2:
                if (N >= 3) {
                    return cruceMultipunto(individuo2);
                } else {
                    throw new TipoCruceNoValidoException();
                }
            default:
                throw new TipoCruceNoValidoException();
        }
    }

    private Individuo[] cruceEnPunto(Individuo individuo2) {
        int cambio = rnd.nextInt(this.coords.length);
        Individuo[] nuevo = new Individuo[]{this.getNewIndividuo(), this.getNewIndividuo()};
        double[] coords_nuevo1 = nuevo[0].getCoords().clone();
        double[] coords_nuevo2 = nuevo[0].getCoords().clone();

        int i;
        for (i = 0; i < cambio; ++i) {
            coords_nuevo1[i] = coords[i];
            coords_nuevo2[i] = individuo2.getCoords()[i];
        }
        for (; i < coords.length; ++i) {
            coords_nuevo1[i] = individuo2.getCoords()[i];
            coords_nuevo2[i] = coords[i];
        }
        nuevo[0].setCoords(coords_nuevo1);
        nuevo[1].setCoords(coords_nuevo2);

        return nuevo;
    }

    private Individuo[] cruceUniforme(Individuo individuo2) {
        // Se hace un random de que coordenada coger para cada posicion
        Individuo[] nuevo = new Individuo[]{this.getNewIndividuo(), this.getNewIndividuo()};
        double[] coords_nuevo = nuevo[0].getCoords();
        double[] coords_nuevo1 = nuevo[0].getCoords();
        for (int i = 0; i < coords_nuevo.length; i++) {
            boolean b = rnd.nextBoolean();
            coords_nuevo[i] = b ? coords[i] : individuo2.getCoords()[i];
            coords_nuevo1[i] = b ? individuo2.getCoords()[i] : coords[i];
        }
        nuevo[0].setCoords(coords_nuevo);
        nuevo[1].setCoords(coords_nuevo);
        return nuevo;

    }

    private Individuo[] cruceMultipunto(Individuo individuo2) {
        // Como el cruce en un punto PERO con dos puntos
        Individuo[] nuevo = new Individuo[]{this.getNewIndividuo(), this.getNewIndividuo()};
        double[] coords_nuevo1 = nuevo[0].getCoords();
        double[] coords_nuevo2 = nuevo[0].getCoords();

        switch (rnd.nextInt(3)) {
            case 0:
                coords_nuevo1[0] = coords[0];
                coords_nuevo1[1] = individuo2.getCoords()[1];
                coords_nuevo1[2] = coords[2];
                coords_nuevo2[0] = individuo2.getCoords()[0];
                coords_nuevo2[1] = coords[1];
                coords_nuevo2[2] = individuo2.getCoords()[2];
                break;
            case 1:
                coords_nuevo1[0] = coords[0];
                coords_nuevo1[1] = individuo2.getCoords()[1];
                coords_nuevo1[2] = individuo2.getCoords()[2];
                coords_nuevo2[0] = individuo2.getCoords()[0];
                coords_nuevo2[1] = coords[1];
                coords_nuevo2[2] = coords[2];
                break;
            case 2:
                coords_nuevo1[0] = coords[0];
                coords_nuevo1[1] = coords[1];
                coords_nuevo1[2] = individuo2.getCoords()[2];
                coords_nuevo2[0] = individuo2.getCoords()[0];
                coords_nuevo2[1] = individuo2.getCoords()[1];
                coords_nuevo2[2] = coords[2];
                break;
        }

        nuevo[0].setCoords(coords_nuevo1);
        nuevo[1].setCoords(coords_nuevo2);

        return nuevo;
    }

    public int compareTo(Individuo o) {
        return Double.compare(getFitness(), o.getFitness());
    }

    @Override
    public String toString() {
        return "Individuo{" +
                "fitness=" + fitness +
                ", coords=" + Arrays.toString(coords) +
                '}';
    }
}
