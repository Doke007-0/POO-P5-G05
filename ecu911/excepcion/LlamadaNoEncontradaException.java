package ecu911.excepcion;

/**
 * Excepción que se lanza cuando no se encuentra una llamada por su código.
 *
 * @author Alfonso Beltran
 */
public class LlamadaNoEncontradaException extends Exception {
    public LlamadaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
