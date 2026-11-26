
package ecu911.controlador;

import ecu911.modelo.*;
import java.util.List;
import java.util.Scanner;

public class LlamadaControlador {

    private Scanner scanner;
    private List<Llamada> listaLlamadas;
    private List<Operador> listaOperadores;
    private List<Ciudadano> listaCiudadanos;

    public LlamadaControlador(
            Scanner scanner,
            List<Llamada> listaLlamadas,
            List<Operador> listaOperadores,
            List<Ciudadano> listaCiudadanos) {

        this.scanner = scanner;
        this.listaLlamadas = listaLlamadas;
        this.listaOperadores = listaOperadores;
        this.listaCiudadanos = listaCiudadanos;
    }

    public void iniciarMenuLlamadas() {

        boolean regresar = false;

        while (!regresar) {

            System.out.println("\n[Administrar llamadas de emergencia]");
            System.out.println("[1] Registrar nueva llamada");
            System.out.println("[2] Regresar al menú principal");
            System.out.print("> Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {

                case 1:
                    registrarLlamada();
                    break;

                case 2:
                    regresar = true;
                    break;

                default:
                    System.out.println("¡Error! Opción fuera de rango.");
            }
        }
    }

    private void registrarLlamada() {

        if (listaOperadores.isEmpty()) {
            System.out.println("No existen operadores registrados.");
            return;
        }

        if (listaCiudadanos.isEmpty()) {
            System.out.println("No existen ciudadanos registrados.");
            return;
        }

        System.out.println("\n[Registrar nueva llamada]");

        // Seleccionar operador
        System.out.println("\nOperadores disponibles:");

        for (Operador operador : listaOperadores) {
            System.out.println(
                    operador.getCodigo() + " - "
                    + operador.getNombreCompleto());
        }

        System.out.print("> Código del operador: ");
        String codigoOperador = scanner.nextLine().trim();

        Operador operadorSeleccionado = null;

        for (Operador operador : listaOperadores) {
            if (operador.getCodigo().equalsIgnoreCase(codigoOperador)) {
                operadorSeleccionado = operador;
                break;
            }
        }

        if (operadorSeleccionado == null) {
            System.out.println("Operador no encontrado.");
            return;
        }

        // Seleccionar ciudadano
        System.out.println("\nCiudadanos registrados:");

        for (Ciudadano ciudadano : listaCiudadanos) {
            System.out.println(
                    ciudadano.getCedula() + " - "
                    + ciudadano.getNombreCompleto());
        }

        System.out.print("> Cédula del ciudadano: ");
        String cedula = scanner.nextLine().trim();

        Ciudadano ciudadanoSeleccionado = null;

        for (Ciudadano ciudadano : listaCiudadanos) {
            if (ciudadano.getCedula().equals(cedula)) {
                ciudadanoSeleccionado = ciudadano;
                break;
            }
        }

        if (ciudadanoSeleccionado == null) {
            System.out.println("Ciudadano no encontrado.");
            return;
        }

        // Seleccionar tipo de emergencia
        System.out.println("\nTipos de emergencia:");

        TipoEmergencia[] tipos = TipoEmergencia.values();

        for (int i = 0; i < tipos.length; i++) {
            System.out.println(
                    "[" + (i + 1) + "] " + tipos[i].getEtiqueta());
        }

        System.out.print("> Seleccione el tipo de emergencia: ");

        int opcionTipo = leerEntero();

        if (opcionTipo < 1 || opcionTipo > tipos.length) {
            System.out.println("Tipo de emergencia inválido.");
            return;
        }

        TipoEmergencia tipoSeleccionado = tipos[opcionTipo - 1];

        // Datos de ubicación y descripción
        System.out.print("> Ciudad: ");
        String ciudad = scanner.nextLine().trim();

        System.out.print("> Barrio: ");
        String barrio = scanner.nextLine().trim();

        System.out.print("> Detalle de la emergencia: ");
        String detalle = scanner.nextLine().trim();

        if (ciudad.isEmpty() || barrio.isEmpty() || detalle.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            return;
        }

        // Crear y registrar llamada
        Llamada nuevaLlamada = new Llamada(
                operadorSeleccionado,
                ciudadanoSeleccionado,
                tipoSeleccionado,
                ciudad,
                barrio,
                detalle
        );

        listaLlamadas.add(nuevaLlamada);
        ciudadanoSeleccionado.agregarLlamada(nuevaLlamada);

        System.out.println("\n¡Llamada registrada exitosamente!");
        System.out.println("Código: " + nuevaLlamada.getCodigo());
        System.out.println("Ciudadano: "
                + ciudadanoSeleccionado.getNombreCompleto());
        System.out.println("Operador: "
                + operadorSeleccionado.getNombreCompleto());
        System.out.println("Estado: " + nuevaLlamada.getEstado());

        esperarEnter();
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void esperarEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}

