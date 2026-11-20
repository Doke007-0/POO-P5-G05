package ecu911.vista;
import ecu911.modelo.Operador;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author Julian
 */
public class OperadorVista {
    private Scanner scanner;

    
    //Constructor de la vista.
     
    public OperadorVista(Scanner scanner) {
        this.scanner = scanner;
    }

    //Muestra el listado de operadores registrados.
     
    public void mostrarOperadores(
            List<Operador> operadores,
            Map<String, Integer> llamadasPorOperador) {

        System.out.println("\nSISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
        System.out.println("[Menu Principal > 2. Administrar operadores]");
        System.out.println("\nListado actual de operadores registrados:");

        System.out.printf("%-10s | %-30s | %-12s | %s%n",
                "CÓDIGO", "NOMBRE COMPLETO", "TURNO",
                "LLAMADAS ATENDIDAS");

        if (operadores.isEmpty()) {
            System.out.println("No existen operadores registrados.");
        } else {
            for (Operador operador : operadores) {

                int cantidad = llamadasPorOperador.getOrDefault(
                        operador.getCodigo(), 0);

                System.out.printf("%-10s | %-30s | %-12s | %d%n",
                        operador.getCodigo(),
                        operador.getNombreCompleto(),
                        operador.getTurno(),
                        cantidad);
            }
        }
    }

    
    //Muestra el menú de administración de operadores.
     
    public int mostrarMenuOperadores() {
        System.out.println("\n¿Qué accion desea realizar?");
        System.out.println("[1] Agregar un nuevo operador");
        System.out.println("[2] Eliminar un operador existente");
        System.out.println("[3] Regresar al Menu Principal");
        System.out.print("> Seleccione una opción (1-3): ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

   
    public String pedirNombres() {
        System.out.print("1. Nombres: ");
        return scanner.nextLine().trim();
    }

  
    public String pedirApellidos() {
        System.out.print("2. Apellidos: ");
        return scanner.nextLine().trim();
    }

   
    public int pedirTurno() {
        System.out.println("3. Turno:");
        System.out.println("[1] Matutino");
        System.out.println("[2] Vespertino");
        System.out.println("[3] Nocturno");
        System.out.print("Seleccione una opción (1-3): ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    
    public String pedirEmail() {
        System.out.print("4. Email (opcional, ENTER para omitir): ");
        return scanner.nextLine().trim();
    }

    
    public String pedirCodigoOperador() {
        System.out.print("> Ingrese el codigo del operador que desea eliminar: ");
        return scanner.nextLine().trim();
    }

  
    public String confirmarEliminacion(String nombre) {
        System.out.print("¿Está seguro de que desea eliminar a "
                + nombre + "? (S/N): ");
        return scanner.nextLine().trim();
    }

  
    public void mostrarMensajeError(String mensaje) {
        System.out.println("¡Error! " + mensaje);
    }

  
    public void mostrarMensajeExito(String mensaje) {
        System.out.println("¡Exito! " + mensaje);
    }

   
    public void esperarEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
