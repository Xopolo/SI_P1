package Modelo.Comunes;

/**
 * Excepcion en caso de que el tipo de poblacion seleccionado no soporte el cruce solicitado
 */
public class TipoCruceNoValidoException extends Exception {
    @Override
    public String getMessage() {
        return "Tipo de eleccion de cruce NO VALIDO";
    }
}
