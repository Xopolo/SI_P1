package Modelo.Individuos;

import Modelo.Comunes.Enums.Cruce;
import Modelo.Comunes.Enums.Mutacion;
import Modelo.Comunes.Individuo;

import java.util.Arrays;
import java.util.Random;

/**
 * La clase Individuo de la funcion Bukin
 */
public class IndividuoBukin extends Individuo {

    private int limiteXizq;
    private int limiteXder;
    private int limiteY;

    /**
     * Constructor de la clase
     *
     * @param tipo_mutacion el tipo de mutacion
     * @param tipo_cruce    el tipo de cruce
     */
    public IndividuoBukin(Mutacion tipo_mutacion, Cruce tipo_cruce) {

        super(tipo_mutacion, tipo_cruce);
        this.limiteXizq = -15;
        this.limiteXder = -5;
        this.limiteY = 3;
        N = 2;
        coords = new double[N];
        coords[0] = -(rnd.nextDouble() * 10) - 5;
        coords[1] = ((rnd.nextDouble() * 2) - 1) * this.limiteY;
    }

    @Override
    public double evaluar() {
        fitness = 100.0 * Math.sqrt(Math.abs(coords[1] - 0.01 * Math.pow(coords[0], 2))) + 0.01 * Math.abs(coords[0] + 10.0);
        return fitness;
    }

    @Override
    public boolean isValido() {

        if (coords[0] < -limiteXizq || coords[0] > limiteXder) {
            valido = false;
        } else if (coords[1] < -limiteY || coords[1] > limiteY) {
            valido = false;
        }
        return valido;
    }

    @Override
    public void mutar() {
        Random rnd = new Random();
        int coord_i = rnd.nextInt(coords.length);
        switch (tipo_mutacion) {
            case UNIFORME:
                coords[coord_i] += (rnd.nextDouble() * 2) - 1;
                break;
            case NORMAL:
                double std_deviation = 1; // Valores por defecto en distribución normal
                double media = 0;
                coords[coord_i] += rnd.nextGaussian() * std_deviation + media;
                break;
            case ALEATORIO:
                if (coord_i == 0) {
                    coords[0] = -(rnd.nextDouble() * limiteXder - limiteXizq) - limiteXder;
                } else {
                    coords[1] = ((rnd.nextDouble() * 2) - 1) * limiteY;
                }
                break;
        }
        isValido();
    }

    protected Individuo getNewIndividuo() {


        return new IndividuoBukin(tipo_mutacion, tipo_cruce);
    }

    @Override
    public String toString() {
        return "IndividuoBukin{" +
                "fitness=" + fitness +
                ", coords=" + Arrays.toString(coords) +
                '}';
    }
}
