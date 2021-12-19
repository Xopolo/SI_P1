package Modelo.Individuos;

import Modelo.Comunes.Enums.Cruce;
import Modelo.Comunes.Enums.Mutacion;
import Modelo.Comunes.Individuo;

import java.util.Arrays;

/**
 * El Individuo de la funcion Ackley
 */
public class IndividuoAckley extends Individuo {

    /**
     * Constructor de individuoAckley
     *
     * @param tipo_mutacion el tipo de mutacion
     * @param tipo_cruce    el tipo de cruce
     */
    public IndividuoAckley(Mutacion tipo_mutacion, Cruce tipo_cruce) {
        super(tipo_mutacion, tipo_cruce);
        this.limite = 5;
        this.valido = true;
        N = 2;
        coords = new double[N];
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

    /**
     * Confirma si los genes del individuo son validos para la funcion
     * @return Si los genes son validos
     */
    public boolean isValido() {
        for (double coord : coords) {
            if (coord < -limite || coord > limite) {
                valido = false;
                break;
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

    @Override
    public String toString() {
        return "IndividuoAckley{" +
                "fitness=" + fitness +
                ", coords=" + Arrays.toString(coords) +
                '}';
    }
}
