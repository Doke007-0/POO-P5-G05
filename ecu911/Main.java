
package ecu911;

import ecu911.controlador.CiudadanoControlador;
import ecu911.controlador.NotaControlador;
import ecu911.controlador.OperadorControlador;
import ecu911.controlador.LlamadaControlador;

import ecu911.modelo.Ciudadano;
import ecu911.modelo.Llamada;
import ecu911.modelo.NotaSeguimiento;
import ecu911.modelo.Operador;
import ecu911.modelo.TipoEmergencia;
import ecu911.modelo.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Punto de entrada del Sistema de Registro de Llamadas ECU 911.
 * Arma las listas de datos (con datos iniciales de ejemplo), crea los
 * controladores que existen en el proyecto y muestra el menú principal.
 */
public final class Main {

    private Main() {
        // Clase de arranque: no se instancia.
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, "UTF-8");

        // Listas compartidas entre los controladores
        List<Operador> operadores = new ArrayList<>();
        List<Ciudadano> ciudadanos = new ArrayList<>();
        List<Llamada> llamadas = new ArrayList<>();
        List<NotaSeguimiento> notas = new ArrayList<>();

        cargarDatosIniciales(operadores, ciudadanos, llamadas, notas);

        // Inicialización de controladores
        LlamadaControlador llamadaControlador =
                new LlamadaControlador(
                        scanner, llamadas, operadores, ciudadanos
                );

        CiudadanoControlador ciudadanoControlador =
                new CiudadanoControlador(scanner, ciudadanos);

        OperadorControlador operadorControlador =
                new OperadorControlador(scanner, operadores, llamadas);

        NotaControlador notaControlador =
                new NotaControlador(scanner, notas, llamadas, operadores);

        try {

            boolean salir = false;

            while (!salir) {

                mostrarMenuPrincipal();

                String opcion = scanner.nextLine().trim();

                switch (opcion) {

                    case "1":
                        llamadaControlador.iniciarMenuLlamadas();
                        break;

                    case "2":
                        operadorControlador.iniciarMenuOperadores();
                        break;

                    case "3":
                        ciudadanoControlador.iniciarMenuCiudadanos();
                        break;

                    case "4":
                        notaControlador.iniciarMenuNotas();
                        break;

                    case "5":
                        salir = true;
                        break;

                    default:
                        System.out.println(
                                "Opción inválida. Ingrese un número del 1 al 5."
                        );
                        break;
                }
            }

        } catch (NoSuchElementException e) {

            // Se cerró la entrada (Ctrl+D / Ctrl+Z)
            System.out.println();
            System.out.println("Entrada finalizada.");
        }

        System.out.println("Cerrando el sistema ECU 911...");
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {

        System.out.println();
        System.out.println("=== SISTEMA DE REGISTRO DE LLAMADAS ECU 911 ===");
        System.out.println("1. Registrar llamada");
        System.out.println("2. Administrar operadores");
        System.out.println("3. Administrar ciudadanos");
        System.out.println("4. Notas de seguimiento");
        System.out.println("5. Salir");

        System.out.print("Seleccione una opción: ");
    }

    /**
     * Crea datos de ejemplo para poder probar los menús.
     */
    private static void cargarDatosIniciales(
            List<Operador> operadores,
            List<Ciudadano> ciudadanos,
            List<Llamada> llamadas,
            List<NotaSeguimiento> notas) {

        // Operadores
        Operador op1 = new Operador(
                "Carlos Andrés", "Mora Vera",
                Turno.MATUTINO, "carlos.mora@ecu911.gob.ec"
        );

        Operador op2 = new Operador(
                "María José", "Pérez Loor",
                Turno.VESPERTINO, "maria.perez@ecu911.gob.ec"
        );

        Operador op3 = new Operador(
                "Luis", "Zambrano Ruiz",
                Turno.NOCTURNO, ""
        );

        operadores.add(op1);
        operadores.add(op2);
        operadores.add(op3);

        // Ciudadanos
        Ciudadano c1 = new Ciudadano(
                "Ana Lucía", "Torres Paredes", "0912345678"
        );

        Ciudadano c2 = new Ciudadano(
                "Jorge", "Villacís Mendoza", "0923456789"
        );

        Ciudadano c3 = new Ciudadano(
                "Rosa", "Alcívar Soria", "0934567890"
        );

        ciudadanos.add(c1);
        ciudadanos.add(c2);
        ciudadanos.add(c3);

        // Llamadas (cada una se enlaza con su ciudadano)
        Llamada l1 = new Llamada(
                op1, c1, TipoEmergencia.TRANSITO,
                "Guayaquil", "Urdesa",
                "Choque entre dos vehículos en la av. Víctor Emilio Estrada"
        );

        Llamada l2 = new Llamada(
                op2, c1, TipoEmergencia.ENERGIA,
                "Guayaquil", "Urdesa",
                "Cable de alta tensión caído en la vereda"
        );

        Llamada l3 = new Llamada(
                op1, c2, TipoEmergencia.CIVIL,
                "Guayaquil", "Alborada",
                "Robo en un local comercial"
        );

        Llamada l4 = new Llamada(
                op3, c3, TipoEmergencia.MEDIO_AMBIENTE,
                "Samborondón", "La Puntilla",
                "Incendio de maleza cerca de viviendas"
        );

        llamadas.add(l1);
        llamadas.add(l2);
        llamadas.add(l3);
        llamadas.add(l4);

        c1.agregarLlamada(l1);
        c1.agregarLlamada(l2);
        c2.agregarLlamada(l3);
        c3.agregarLlamada(l4);

        // Una nota de ejemplo
        notas.add(
                new NotaSeguimiento(
                        op1,
                        "Se despachó una unidad de tránsito al lugar.",
                        l1
                )
        );
    }
}
