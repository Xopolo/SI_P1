package Comunes;

public class Main {

    int TAM_POBLACION;
    int NUM_GENERACIONES;
    double PROB_CRUCE;
    double PROB_MUTACION;
    int NUM_ELITISTAS;

    int ESTRATEGIA_REEMPLAZAMIENTO;
    int TIPO_OPERADOR_CRUCE;
    int TIPO_OPERADOR_MUTACION;
    int TIPO_SELECCION_INDIVIUOS;

    {
        TAM_POBLACION = 200;
        NUM_GENERACIONES = 2000;
        PROB_CRUCE = 0.7;
        PROB_MUTACION = 0.05;
        NUM_ELITISTAS = 4;
        TIPO_SELECCION_INDIVIUOS = 0;
        ESTRATEGIA_REEMPLAZAMIENTO = 0;
        TIPO_OPERADOR_MUTACION = 0;
        TIPO_OPERADOR_CRUCE = 1;
    }


    public static void main(String[] args) {
        Main r = new Main();
        r.run();
    }

    public void run() {
        Poblacion poblacion = new Poblacion(TAM_POBLACION, NUM_GENERACIONES, PROB_CRUCE, PROB_MUTACION, NUM_ELITISTAS,
                TIPO_SELECCION_INDIVIUOS, TIPO_OPERADOR_MUTACION, TIPO_OPERADOR_CRUCE);
        poblacion.generarPoblacion();
        Individuo[] nuevaPoblacion = new Individuo[TAM_POBLACION];
        Individuo[] individuos = new Individuo[2];
//        Individuo[] individuosCruzados = new Individuo[2];

        //Comunes.Poblacion actual
        System.out.println("Iteracion: 0");
        System.out.println("Total Fitness = " + poblacion.getFitness_total());
        System.out.println("Mejor Fitness = "
                + poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness());
        System.out.print("Mejor Individuo: ");
        System.out.println(poblacion.getIndividuoConMejorFitnessEnPos(0).toString());

        int count;
        for (int num_iteracion = 0; num_iteracion < NUM_GENERACIONES; num_iteracion++) {
            count = 0;
            nuevaPoblacion = new Individuo[TAM_POBLACION];
            for (int j = 0; j < NUM_ELITISTAS; ++j) {
                nuevaPoblacion[count] = poblacion.getIndividuoConMejorFitnessEnPos(j);
                count++;
            }

            while (count < TAM_POBLACION) {

                // Seleccion
                individuos[0] = poblacion.seleccionIndividuos();
                individuos[1] = poblacion.seleccionIndividuos();

                // Cruce
                if (poblacion.getNextDouble() < this.PROB_CRUCE) {
                    try {
                        individuos = individuos[0].cruzar(individuos[1]);
                    } catch (TipoCruceNoValidoException e) {
                        System.out.println("No se puede realizar el cruce");
                        e.printStackTrace();
                    }
                }

                // Mutacion
                if (poblacion.getNextDouble() < PROB_MUTACION) {
                    individuos[0].mutar();
                }

                if (poblacion.getNextDouble() < PROB_MUTACION) {
                    individuos[1].mutar();
                }

                nuevaPoblacion[count] = individuos[0];
                nuevaPoblacion[count + 1] = individuos[1];
                count += 2;
            }

            if (ESTRATEGIA_REEMPLAZAMIENTO == 0) {
                // Esquema generacional

                poblacion.setPoblacion(nuevaPoblacion.clone());
            } else {
                // TODO Hay que mezclar ambas poblaciones
            }

            poblacion.evaluar();
            System.out.println("******************************************************************" +
                    "*****************************************************************************");
            System.out.println("Iteracion: " + (num_iteracion + 1));
            System.out.println("Total Fitness = " + poblacion.getFitness_total());
            System.out.println("Mejor Fitness = "
                    + poblacion.getIndividuoConMejorFitnessEnPos(0).getFitness());
            System.out.print("Mejor Individuo: ");
            System.out.println(poblacion.getIndividuoConMejorFitnessEnPos(0).toString());


        }

    }

}
