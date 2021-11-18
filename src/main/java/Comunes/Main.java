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
        NUM_ELITISTAS = 3;
        TIPO_OPERADOR_CRUCE = 0;

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
        Individuo[] individuosCruzados = new Individuo[2];

        //Comunes.Poblacion actual
        System.out.println("Iteracion: 0");
        System.out.println("Fitness total: " + poblacion.getFitness_total());
        Individuo individuoConMejorFitnessEnPos = poblacion.getIndividuoConMejorFitnessEnPos(0);
        System.out.println("Mejor Individuo: " + individuoConMejorFitnessEnPos.toString());
        System.out.println("Mejor Fitness individual: " + individuoConMejorFitnessEnPos.getFitness());

        System.out.println("Pruebas cola");
        for (int i = 0; i < NUM_ELITISTAS; i++) {
            System.out.println(poblacion.getIndividuoConMejorFitnessEnPos(i));
        }

        int count;
        for (int num_iteracion = 0; num_iteracion < NUM_GENERACIONES; num_iteracion++) {
            count = 0;

            for (int j = 0; num_iteracion < NUM_ELITISTAS; ++num_iteracion) {
                nuevaPoblacion[count] = poblacion.getIndividuoConMejorFitnessEnPos(j);
                count++;
            }

            while (count < TAM_POBLACION) {

                // Seleccion
                individuos[0] = poblacion.seleccionIndividuos();
                individuos[1] = poblacion.seleccionIndividuos();

                // Cruce
                if(poblacion.getNextDouble() < this.PROB_CRUCE){
                    try {
                        individuosCruzados = individuos[0].cruzar(individuos[1]);
                    } catch (TipoCruceNoValidoException e) {
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


                // TODO Ver tipo reemplazamiento
                nuevaPoblacion =

            }

        }

    }

}
