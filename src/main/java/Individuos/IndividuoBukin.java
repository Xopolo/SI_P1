package Individuos;

import Comunes.Individuo;

import java.util.Random;

public class IndividuoBukin extends Individuo {

    private int limiteXizq;
    private int limiteXder;
    private int limiteY;

    public IndividuoBukin(int tipo_mutacion, int tipo_cruce) {
        this(tipo_mutacion, tipo_cruce, -15, -5, 3);
    }

    public IndividuoBukin(int tipo_mutacion, int tipo_cruce, int limiteXizq, int limiteXder, int limiteY) {

        super(tipo_mutacion, tipo_cruce);
        this.limiteXizq = limiteXizq;
        this.limiteXder = limiteXder;
        this.limiteY = limiteY;
        N = 2;
        coords = new double[N];
        Random rnd = new Random(System.nanoTime());
        coords[0] = -(rnd.nextDouble() * 10) - 5;
        coords[1] = ((rnd.nextDouble() * 2) - 1) * limiteY;
    }

    @Override
    public double evaluar() {
        fitness = 100.0 * Math.sqrt(Math.abs(coords[1]) - 0.01 * Math.pow(coords[0], 2)) + 0.01 * Math.abs(coords[0] + 10.0);
        return fitness;
    }

    //TODO Hacer con limites
    @Override
    public boolean isValido() {
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < -limite || coords[i] > limite) {
                valido = false;
            }
        }

        return valido;
    }

    @Override
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
}
