package Comunes;

public abstract class Individuo {

    protected Double fitness;
    protected final int tipo_mutacion;

    public Individuo(int tipo_mutacion) {
        this.tipo_mutacion = tipo_mutacion;
    }

    public double getFitness(){
        return fitness;
    }

    public abstract double evaluar();

    public abstract void mutar();


}
