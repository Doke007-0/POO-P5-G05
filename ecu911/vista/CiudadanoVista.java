package ecu911.vista;

import ecu911.modelo.Ciudadano;
import ecu911.modelo.Llamada;
import ecu911.util.RelojSistema;
import java.util.List;
import java.util.Scanner;

//aca se va a mostrar la info de administrar ciudadano
public class CiudadanoVista {

    private final Scanner scanner;

//vista
    public CiudadanoVista(Scanner scanner) {
        this.scanner = scanner;
    }

//encabezado y listado
    //cedula, nombre y total de llamadas realizadas.
    public void mostrarCiudadanos(List<Ciudadano> ciudadanos) {
        System.out.println("\nSISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
        System.out.println("[Menú Principal > 3. Administrar ciudadanos]");
        System.out.println("\nListado de ciudadanos registrados:");

        if (ciudadanos.isEmpty()) {
            System.out.println("No existen ciudadanos registrados.");
            return;
        }

        int anchoNombre = "NOMBRE COMPLETO".length();
        for (Ciudadano ciudadano : ciudadanos) {
            anchoNombre = Math.max(anchoNombre,
                    ciudadano.getPrimerNombreYApellidos().length());
        }

        String formato = "%-10s | %-" + anchoNombre + "s | %s%n";
        System.out.printf(formato, "CÉDULA", "NOMBRE COMPLETO", "TOTAL LLAMADAS");
        for (Ciudadano ciudadano : ciudadanos) {
            System.out.printf(formato,
                    ciudadano.getCedula(),
                    ciudadano.getPrimerNombreYApellidos(),
                    ciudadano.getTotalLlamadas());
        }
    }

//opciones del modulo 
    public int mostrarMenuCiudadanos() {
        System.out.println("\n¿Qué acción desea realizar?");
        System.out.println("[1] Consultar historial de llamadas de un ciudadano");
        System.out.println("[2] Regresar al Menú Principal");
        System.out.print("> Seleccione una opción (1-2): ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

//encabezado de la consulta del historial
    public void mostrarEncabezadoConsulta() {
        System.out.println("\n[Administrar ciudadanos > Consultar historial de llamadas]");
    }

//solicita la cedula del ciudadano
    public String pedirCedula() {
        System.out.print("> Ingrese la cédula del ciudadano "
                + "(10 dígitos sin guiones, ej. 0912345678; "
                + "ENTER para regresar): ");
        return scanner.nextLine().trim();
    }

//muestra el historial de llamadas aca
    public void mostrarHistorial(Ciudadano ciudadano, List<Llamada> historial) {
        System.out.println("\nHISTORIAL DE LLAMADAS - "
                + ciudadano.getPrimerNombreYApellidos()
                + " (C.I. " + ciudadano.getCedula() + ")");

        if (historial.isEmpty()) {
            System.out.println("Este ciudadano no tiene llamadas registradas.");
            return;
        }

        int anchoBarrio = "BARRIO".length();
        for (Llamada llamada : historial) {
            anchoBarrio = Math.max(anchoBarrio, llamada.getBarrio().length());
        }

        String formato = "%-8s | %-18s | %-14s | %-" + anchoBarrio + "s | %s%n";
        System.out.printf(formato,
                "CÓDIGO", "FECHA Y HORA", "TIPO", "BARRIO", "ESTADO");
        for (Llamada llamada : historial) {
            System.out.printf(formato,
                    llamada.getCodigo(),
                    RelojSistema.formatear(llamada.getFechaHora()),
                    llamada.getTipoEmergencia(),
                    llamada.getBarrio(),
                    llamada.getEstado());
        }
    }

//error con un formato 
    public void mostrarMensajeError(String mensaje) {
        System.out.println("¡Error! " + mensaje);
    }

//pausa para regresar a admin. ciudadanos
    public void esperarEnter() {
        System.out.print("\nPresione ENTER para regresar a Administrar ciudadanos...");
        scanner.nextLine();
    }
}
