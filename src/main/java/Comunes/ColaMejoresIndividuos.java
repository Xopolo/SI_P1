package Comunes;

class ColaMejoresIndividuos {
    Individuo[] cola;
    double fitnessMasAlto;

    public ColaMejoresIndividuos(int size) {
        cola = new Individuo[size];
        fitnessMasAlto = Integer.MAX_VALUE;

    }

    public boolean add(Individuo individuo) {
        if (individuo.getFitness() > fitnessMasAlto) return false;

        for (int i = 0; i < cola.length; i++) {
            if (cola[i] == null) {
                cola[i] = individuo;
                break;
            }
            if (cola[i].getFitness() > individuo.getFitness()) {
                desplazar(i);
                cola[i] = individuo;
                break;
            }
        }

        double mayorFitEnCola = 0;
        for (int i = 0; i < cola.length; i++) {
            if (cola[i] == null) continue;
            mayorFitEnCola = Math.max(cola[i].getFitness(), mayorFitEnCola);
        }
        fitnessMasAlto = mayorFitEnCola;
        return true;
    }

    public void desplazar(int posicion) {
        if (cola.length - 1 - posicion >= 0)
            System.arraycopy(cola, posicion, cola, posicion + 1, cola.length - 1 - posicion);
    }

    public Individuo get(int index) {
        return cola[index];
    }

}
