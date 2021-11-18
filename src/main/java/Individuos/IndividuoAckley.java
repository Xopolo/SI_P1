package Individuos;

import Comunes.Individuo;

import java.util.Random;

public class IndividuoAckley extends Individuo {
    private double[] coords;

    public IndividuoAckley(int tipo_mutacion, int tipo_cruce) {
        this(tipo_mutacion, tipo_cruce, 5);
    }

    public IndividuoAckley(int tipo_mutacion, int tipo_cruce, double limite) {
        super(tipo_mutacion, tipo_cruce);
        this.limite = limite;
        this.valido = true;
        N = 2;
        coords = new double[N];
        Random rnd = new Random(System.nanoTime());
        for (int i = 0; i < coords.length; i++) {
            coords[i] = ((rnd.nextDouble() * 2) - 1) * this.limite;
        }
    }


    @Override
    public double evaluar() {
        this.fitness = -20.0 * Math.exp(-0.2 * Math.sqrt(0.5 * (Math.pow(coords[0], 2) + Math.pow(coords[1], 2)))) -
                Math.exp(0.5 * (Math.cos(2 * Math.PI * coords[0]) + Math.cos(2 * Math.PI * coords[1]))) + Math.E + 20.0;
        return fitness;
    }

    public boolean isValido() {
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < -limite || coords[i] > limite) {
                valido = false;
            }
        }

        return valido;
    }

    protected Individuo getNewIndividuo() {
        return new IndividuoAckley(tipo_mutacion, tipo_cruce);
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }
}
