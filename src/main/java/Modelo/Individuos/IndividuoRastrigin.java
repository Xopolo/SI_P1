package Modelo.Individuos;

import Modelo.Comunes.Enums.Cruce;
import Modelo.Comunes.Enums.Mutacion;
import Modelo.Comunes.Individuo;

import java.util.Arrays;

/**
 * El Individuo de la funcion Rastrigin
 */
public class IndividuoRastrigin extends Individuo {
    private final int A = 10; // Segun el enunciado


    /**
     * Constructor
     *
     * @param tipo_mutacion el tipo de mutacion
     * @param tipo_cruce    el tipo de cruce
     */
    public IndividuoRastrigin(Mutacion tipo_mutacion, Cruce tipo_cruce) {
        super(tipo_mutacion, tipo_cruce);
        this.valido = true;
        this.limite = 5.12d;
        N = 3;
        coords = new double[N];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = ((rnd.nextDouble() * 2) - 1) * this.limite;
        }
    }

    /**
     * Metodo que evalua el individuo
     * @return El fitness del individuo
     */

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
