package Comunes;

public class TipoCruceNoValidoException extends Exception {
    @Override
    public String getMessage() {
        return "Tipo de eleccion de cruce NO VALIDO";
    }
}
