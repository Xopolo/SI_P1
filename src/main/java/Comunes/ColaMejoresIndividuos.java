package Comunes;


import java.util.TreeSet;

class ColaMejoresIndividuos extends TreeSet<Individuo> {
    private int limite;

    public ColaMejoresIndividuos(int size) {
        this.limite = size;
    }

    @Override
    public boolean add(Individuo e) {
        if (size() > limite && last().getFitness() <= e.getFitness()) {
            return false;
        } else {
            if(contains(e)) return false;
            if(size() == limite) remove(last());
            return super.add(e);
        }
    }

    public Individuo get(int index) {
        return (Individuo) toArray()[index];
    }

}
