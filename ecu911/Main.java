package ecu911;

import ecu911.controlador.ControladorPrincipal;
import ecu911.excepcion.EntradaFinalizadaException;
import ecu911.modelo.RegistroEmergencias;
import ecu911.vista.Consola;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Punto de entrada del Sistema de Registro de Llamadas ECU 911.
 * Solo arma las piezas (modelo, consola y controlador principal) y las
 * pone en marcha; toda la lógica vive en las demás clases.
 */
public final class Main {

    private Main() {
        // Clase de arranque: no se instancia.
    }

    /**
     * Inicia la aplicación de consola.
     *
     * 
     */
    public static void main(String[] args) {
        // Salida en UTF-8 para que se vean bien tildes y la letra ñ.
        PrintStream salida = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        RegistroEmergencias registro = new RegistroEmergencias();
        registro.cargarDatosIniciales();

        Consola consola = new Consola(System.in, salida);
        try {
            new ControladorPrincipal(registro, consola).iniciar();
        } catch (EntradaFinalizadaException e) {
            salida.println();
            salida.println("Entrada finalizada. Cerrando el sistema...");
        }
    }
}
