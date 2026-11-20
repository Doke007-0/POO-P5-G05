package ecu911.excepcion;

/**
 * Excepción que se lanza cuando un dato ingresado no cumple las validaciones.
 *
 * @author Alfonso Beltran
 */
public class DatoInvalidoException extends Exception {
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
