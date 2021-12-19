package Modelo.Comunes;

import Modelo.Comunes.Enums.Cruce;
import Modelo.Comunes.Enums.Mutacion;

import java.util.Arrays;
import java.util.Random;

/**
 * Clase Abstracta Individuo
 */
public abstract class Individuo implements Comparable<Individuo> {

    /**
     * El tipo de mutación a aplicar
     */
    protected final Mutacion tipo_mutacion;
    /**
     * El tipo de cruce a aplicar
     */
    protected final Cruce tipo_cruce;
    /**
     * El fitness del Individuo
     */
    protected Double fitness;
    /**
     * Las coordenadas (genes) del Individuo
     */
    protected double[] coords;
    /**
     * El limite de la funcion
     */
    protected double limite;
    /**
     * Booleano que indica si los genes son validos
     */
    protected boolean valido;
    /**
     * Valor para el calculo de la funcion
     */
    protected int N;
    /**
     * Un random para calcular valores aleatorios
     */
    protected Random rnd;

    /**
     * Constructor parametrizado de la Clase Individuo
     *
     * @param tipo_mutacion El tipo de mutacion
     * @param tipo_cruce    El tipo de cruce
     */
    public Individuo(Mutacion tipo_mutacion, Cruce tipo_cruce) {

        rnd = new Random(System.nanoTime());
        valido = true;
        this.tipo_mutacion = tipo_mutacion;
        this.tipo_cruce = tipo_cruce;
    }

    /**
     * Gets fitness.
     *
     * @return the fitness
     */
    public Double getFitness() {
        return fitness;
    }

    /**
     * Get coords double [ ].
     *
     * @return the double [ ]
     */
    public double[] getCoords() {
        return coords;
    }

    /**
     * Sets coords.
     *
     * @param coords the coords
     */
    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    /**
     * Gets new individuo.
     *
     * @return the new individuo
     */
    protected abstract Individuo getNewIndividuo();

    /**
     * Is valido boolean.
     *
     * @return the boolean
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

    /**
     * Metodo que evalua un individuo
     *
     * @return El fitness asociado al individuo
     */
    public abstract double evaluar();

    /**
     * Metodo que muta un individuo
     */
    public void mutar() {
        Random rnd = new Random();
        int coord_i = rnd.nextInt(coords.length);
        switch (tipo_mutacion) {
            case UNIFORME: //
                coords[coord_i] += (rnd.nextDouble() * 2) - 1;
                break;
            case NORMAL:
                double std_deviation = 1; // Valores por defecto en distribución normal
                double media = 0;
                coords[coord_i] += rnd.nextGaussian() * std_deviation + media;
                break;
            case ALEATORIO:
                coords[coord_i] = ((rnd.nextDouble() * 2) - 1) * limite;
                break;
        }
        isValido();
    }

    /**
     * Método que cruza dos individuos
     *
     * @param individuo2 El individuo con el que se ha de cruzar
     * @return the individuo [ ] Los nuevos individuos resultados del cruce
     * @throws TipoCruceNoValidoException En caso de que el cruce no sea posible
     */
    public Individuo[] cruzar(Individuo individuo2) throws TipoCruceNoValidoException {

        if (this.getClass() != individuo2.getClass()) return null;
        switch (tipo_cruce) {
            case PUNTO: // Cruce en un punto
                return cruceEnPunto(individuo2);
            case UNIFORME: // Cruce uniforme:
                return cruceUniforme(individuo2);
            case MULTIPUNTO:
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
        double[] coords_nuevo = nuevo[0].getCoords().clone();
        double[] coords_nuevo1 = nuevo[0].getCoords().clone();
        for (int i = 0; i < coords_nuevo.length; i++) {
            boolean b = rnd.nextBoolean();
            coords_nuevo[i] = b ? coords[i] : individuo2.getCoords()[i];
            coords_nuevo1[i] = b ? individuo2.getCoords()[i] : coords[i];
        }
        nuevo[0].setCoords(coords_nuevo);
        nuevo[1].setCoords(coords_nuevo1);
        return nuevo;

    }

    private Individuo[] cruceMultipunto(Individuo individuo2) {
        // Como el cruce en un punto PERO con dos puntos
        Individuo[] nuevo = new Individuo[]{this.getNewIndividuo(), this.getNewIndividuo()};
        double[] coords_nuevo1 = nuevo[0].getCoords().clone();
        double[] coords_nuevo2 = nuevo[0].getCoords().clone();

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

    /**
     * Metodo para comparar el fitness de dos individuos
     * @param o El individuo con el que comparar
     * @return Si es menor o mayor el fitness
     */
    public int compareTo(Individuo o) {
        return Double.compare(getFitness(), o.getFitness());
    }

    /**
     * Metodo ToString de la clase individuo
     * @return Una cadena con los parametros del individuo
     */
    @Override
    public String toString() {
        return "Individuo{" +
                "fitness=" + fitness +
                ", coords=" + Arrays.toString(coords) +
                '}';
    }

    /**
     * Metodo equals de individuo
     * @param o El objeto con el que se compara
     * @return Si son iguales o no
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Individuo)) return false;

        Individuo individuo = (Individuo) o;

        if (getFitness() != null ? !getFitness().equals(individuo.getFitness()) : individuo.getFitness() != null)
            return false;
        return Arrays.equals(getCoords(), individuo.getCoords());
    }
     /**
      * Funcion hashcode para Individuo, sirve para ordenar en el SortedTreeSet
      */
    @Override
    public int hashCode() {
        int result = getFitness() != null ? getFitness().hashCode() : 0;
        result = 31 * result + Arrays.hashCode(getCoords());
        return result;
    }
}
