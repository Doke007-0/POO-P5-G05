package ecu911.vista;

import ecu911.modelo.NotaSeguimiento;
import ecu911.modelo.Operador;
import java.util.List;
import java.util.Scanner;

/**
 * Vista para la interfaz de consola del Menú 4 (Notas de seguimiento).
 */
public class NotaVista {
    private Scanner scanner;

    public NotaVista() {
        this.scanner = new Scanner(System.in);
    }

    public String pedirCodigoLlamada() {
        System.out.println("\nSISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
        System.out.println("[Menú Principal > 4. Notas de seguimiento]");
        System.out.print("> Ingrese el código de la llamada (ej. LL-001): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public void mostrarNotasDeLlamada(String codigoLlamada, List<NotaSeguimiento> notas) {
        System.out.println("\nNotas de la llamada " + codigoLlamada + " (Ordenadas desde la más reciente):");
        System.out.println("FECHA Y HORA     | OPERADOR        | NOTA");

        if (notas.isEmpty()) {
            System.out.println("No hay notas registradas para esta llamada.");
        } else {
            // Se recorre al revés para mostrar primero las notas más recientes[cite: 13]
            for (int i = notas.size() - 1; i >= 0; i--) {
                NotaSeguimiento n = notas.get(i);
                // Si Persona tiene getNombres() y getApellidos() en lugar de getNombreCompleto(), lo armamos aquí:
                String nombreOp = n.getOperador().getNombres() + " " + n.getOperador().getApellidos();
                System.out.println(n.getFechaHoraFormateada() + " | " + nombreOp + " | " + n.getTexto());
            }
        }
    }

    public int mostrarSubMenuNotas() {
        System.out.println("\n¿Qué acción desea realizar?");
        System.out.println("[1] Añadir una nueva nota");
        System.out.println("[2] Regresar al Menú Principal");
        System.out.print("> Seleccione una opción (1-2): ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int pedirOperador(List<Operador> operadores) {
        System.out.println("\n[Notas de seguimiento > Añadir nota]");
        System.out.println("Seleccione el operador que registra la nota:");
        for (int i = 0; i < operadores.size(); i++) {
            Operador op = operadores.get(i);
            System.out.println("[" + (i + 1) + "] " + op.getNombres() + " " + op.getApellidos());
        }
        System.out.print("Seleccione el número de operador: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String pedirTextoNota(String fechaActual) {
        System.out.println("Fecha y hora actuales del registro: " + fechaActual);
        System.out.println("> Ingrese el texto de la nota:");
        return scanner.nextLine().trim();
    }

    public void mostrarExitoNota(NotaSeguimiento nota) {
        String nombreOp = nota.getOperador().getNombres() + " " + nota.getOperador().getApellidos();
        System.out.println("\nGUARDANDO NOTA...");
        System.out.println("¡Éxito! La nota ha sido guardada en la llamada " + nota.getLlamada().getCodigo() + ":");
        System.out.println("-> Registro: " + nota.getFechaHoraFormateada());
        System.out.println("-> Operador: " + nombreOp);
        System.out.print("\nPresione ENTER para actualizar y volver a Notas de seguimiento...");
        scanner.nextLine();
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println("¡Error! " + mensaje);
    }
}Carpeta vista
