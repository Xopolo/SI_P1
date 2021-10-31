package Individuos;

import Comunes.Individuo;

import java.util.Arrays;
import java.util.Random;

public class IndividuoRastrigin extends Individuo {
    private double[] coords;
    private final int A = 10; // Segun el enunciado
    private final int N = 3;
    private final double limite;
    private boolean valido;

    @Deprecated
    public IndividuoRastrigin(double[] coords, double limite, int tipo_mutacion) {
        super(tipo_mutacion);
        this.coords = coords;
        this.limite = limite;
    }

    public IndividuoRastrigin(int tipo_mutacion) {
        this(5.12d, tipo_mutacion);
    }

    public IndividuoRastrigin(double limite, int tipo_mutacion) {
        super(tipo_mutacion);
        this.valido = true;
        this.limite = limite;
        coords = new double[N];
        Random rnd = new Random(System.nanoTime());
        for (int i = 0; i < coords.length; i++) {
            coords[i] = ((rnd.nextDouble() * 2) - 1) * this.limite;
        }
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public double evaluar() {
        double value = 0;
        double x;
        for (double coord : coords) {
            x = coord;
            value += Math.pow(x, 2) - (A * Math.cos(2 * Math.PI * x));
        }
        fitness = value + (A * N);
        return fitness;
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
        compSiValido();

    }

    private void compSiValido() {
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < -limite || coords[i] > limite) {
                valido = false;
            }
        }
    }

    public boolean isValido() {
        return valido;
    }

    @Override
    public String toString() {
        return "IndividuoRastrigin{" +
                "fitness=" + fitness +
                ", tipo_mutacion=" + tipo_mutacion +
                ", coords=" + Arrays.toString(coords) +
                '}';
    }
}
