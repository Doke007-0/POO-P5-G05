package ecu911.controlador;
import ecu911.modelo.Operador;
import ecu911.modelo.Llamada;
import ecu911.modelo.Turno;
import ecu911.util.Validador;
import ecu911.vista.OperadorVista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Julian
 */
public class OperadorControlador {
    private OperadorVista vista;
    private List<Operador> listaOperadores;
    private List<Llamada> listaLlamadas;

    /**
     * Constructor del controlador.
    */
    public OperadorControlador(
            Scanner scanner,
            List<Operador> listaOperadores,
            List<Llamada> listaLlamadas) {

        this.vista = new OperadorVista(scanner);
        this.listaOperadores = listaOperadores;
        this.listaLlamadas = listaLlamadas;
    }

    /**
     * Inicia el menú de administración de operadores.
    */
    public void iniciarMenuOperadores() {

        boolean regresar = false;

        while (!regresar) {

            vista.mostrarOperadores(
                    listaOperadores,
                    obtenerLlamadasPorOperador());

            int opcion = vista.mostrarMenuOperadores();

            switch (opcion) {

                case 1:
                    agregarOperador();
                    break;

                case 2:
                    eliminarOperador();
                    break;

                case 3:
                    regresar = true;
                    break;

                default:
                    vista.mostrarMensajeError(
                            "Opcion fuera de rango. Ingrese 1, 2 o 3.");
                    break;
            }
        }
    }

    /**
     * Registra un nuevo operador después de validar sus datos.
    */
    private void agregarOperador() {

        System.out.println("\n[Administrar operadores > Agregar operador]");

        String nombres;
        do {
            nombres = vista.pedirNombres();

            if (!Validador.noVacio(nombres)) {
                vista.mostrarMensajeError(
                        "Los nombres son obligatorios.");
            }

        } while (!Validador.noVacio(nombres));

        String apellidos;
        do {
            apellidos = vista.pedirApellidos();

            if (!Validador.noVacio(apellidos)) {
                vista.mostrarMensajeError(
                        "Los apellidos son obligatorios.");
            }

        } while (!Validador.noVacio(apellidos));

        Turno turno = null;

        while (turno == null) {

            int opcionTurno = vista.pedirTurno();

            switch (opcionTurno) {

                case 1:
                    turno = Turno.MATUTINO;
                    break;

                case 2:
                    turno = Turno.VESPERTINO;
                    break;

                case 3:
                    turno = Turno.NOCTURNO;
                    break;

                default:
                    vista.mostrarMensajeError(
                            "Turno invalido. Seleccione 1, 2 o 3.");
                    break;
            }
        }

        String email;

        while (true) {

            email = vista.pedirEmail();

            if (email.isEmpty() || Validador.esEmailValido(email)) {
                break;
            }

            vista.mostrarMensajeError(
                    "El correo electronico no tiene un formato valido.");
        }

        Operador nuevoOperador = new Operador(
                nombres, apellidos, turno, email);
        
        listaOperadores.add(nuevoOperador);

        vista.mostrarMensajeExito(
                "El operador " + nuevoOperador.getNombreCompleto()
                + " ha sido registrado con el código "
                + nuevoOperador.getCodigo() + ".");
        
        vista.esperarEnter();
    }

    /**
     * Elimina un operador si no tiene llamadas registradas.
    */
    private void eliminarOperador() {

        if (listaOperadores.isEmpty()) {
            vista.mostrarMensajeError(
                    "No existen operadores para eliminar.");
            vista.esperarEnter();
            return;
        }

        System.out.println("\n[Administrar operadores > Eliminar operador]");

        boolean regresar = false;

        while (!regresar) {

            String codigo = vista.pedirCodigoOperador();

            Operador operador = buscarOperadorPorCodigo(codigo);

            if (operador == null) {
                vista.mostrarMensajeError(
                        "No existe un operador con el código " + codigo + ".");
                regresar = true;
                continue;
            }

            int cantidadLlamadas = contarLlamadas(operador);

            if (cantidadLlamadas > 0) {

                vista.mostrarMensajeError(
                        "No se puede eliminar. El operador "
                        + operador.getNombreCompleto()
                        + " tiene " + cantidadLlamadas
                        + " llamadas registradas.");

                regresar = true;

            } else {

                String respuesta = vista.confirmarEliminacion(
                        operador.getNombreCorto());

                if (respuesta.equalsIgnoreCase("S")) {

                    listaOperadores.remove(operador);

                    vista.mostrarMensajeExito(
                            "El registro del operador ha sido removido.");

                } else if (respuesta.equalsIgnoreCase("N")) {

                    System.out.println("Operacion cancelada.");

                } else {

                    vista.mostrarMensajeError(
                            "Respuesta invalida. La operacion fue cancelada.");
                }

                regresar = true;
            }
        }

        vista.esperarEnter();
    }


    private Operador buscarOperadorPorCodigo(String codigo) {

        for (Operador operador : listaOperadores) {

            if (operador.getCodigo().equalsIgnoreCase(codigo)) {
                return operador;
            }
        }

        return null;
    }

    private int contarLlamadas(Operador operador) {

        int contador = 0;

        for (Llamada llamada : listaLlamadas) {

            if (llamada.getOperador() != null
                    && llamada.getOperador().getCodigo()
                    .equals(operador.getCodigo())) {

                contador++;
            }
        }

        return contador;
    }

    private Map<String, Integer> obtenerLlamadasPorOperador() {

        Map<String, Integer> resultado = new HashMap<>();

        for (Operador operador : listaOperadores) {

            resultado.put(
                    operador.getCodigo(),
                    contarLlamadas(operador));
        }

        return resultado;
    }
}
