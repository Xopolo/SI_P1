package Individuos;

import Comunes.Individuo;

import java.util.Arrays;
import java.util.Random;

public class IndividuoRastrigin extends Individuo {
    private final int A = 10; // Segun el enunciado


    public IndividuoRastrigin(int tipo_mutacion, int tipo_cruce) {
        super(tipo_mutacion, tipo_cruce);
        this.valido = true;
        this.limite = 5.12d;
        N = 3;
        coords = new double[N];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = ((rnd.nextDouble() * 2) - 1) * this.limite;
        }
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
                ", coords=" + Arrays.toString(coords) +
                '}';
    }
}
