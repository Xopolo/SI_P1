package Individuos;

import Comunes.Individuo;

import java.util.Arrays;
import java.util.Random;

public class IndividuoBeale extends Individuo {

    public IndividuoBeale(int tipo_mutacion, int tipo_cruce) {
        super(tipo_mutacion, tipo_cruce);
        this.valido = true;
        this.limite = 4.5d;
        N = 2;
        coords = new double[N];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = ((rnd.nextDouble() * 2) - 1) * this.limite;
        }
    }

    @Override
    public double evaluar() {
        this.fitness = Math.pow((1.5 - coords[0] + coords[0] * coords[1]), 2) + Math.pow((2.25 - coords[0] + coords[0] * Math.pow(coords[1], 2)), 2)
                + Math.pow((2.625 - coords[0] + coords[0] * Math.pow(coords[1], 3)), 2);
        return fitness;

    }

    protected Individuo getNewIndividuo() {
        return new IndividuoBeale(tipo_mutacion, tipo_cruce);
    }

    @Override
    public String toString() {
        return "IndividuoBeale{" +
                "fitness=" + fitness +
                ", coords=" + Arrays.toString(coords) +
                '}';
    }
}
