package Individuos;

import Comunes.Individuo;

import java.util.Arrays;
import java.util.Random;

public class IndividuoRastrigin extends Individuo {
    private final int A = 10; // Segun el enunciado
    private final int N = 3;
    private final double limite;

    public IndividuoRastrigin(int tipo_mutacion, int tipo_cruce) {
        this(5.12d, tipo_mutacion, tipo_cruce);
    }

    public IndividuoRastrigin(double limite, int tipo_mutacion, int tipo_cruce) {
        super(tipo_mutacion, tipo_cruce);
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

    protected Individuo getNewIndividuo() {
        return new IndividuoRastrigin(tipo_mutacion, tipo_cruce);
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
