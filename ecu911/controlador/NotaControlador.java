package ecu911.controlador;

import ecu911.modelo.Llamada;
import ecu911.modelo.NotaSeguimiento;
import ecu911.modelo.Operador;
import ecu911.util.RelojSistema;
import ecu911.util.Validador;
import ecu911.vista.NotaVista;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que maneja las operaciones del Menú 4 (Notas de seguimiento).
 */
public class NotaControlador {
    private NotaVista vista;
    private List<NotaSeguimiento> listaNotas;
    private List<Llamada> listaLlamadas;
    private List<Operador> listaOperadores;

    public NotaControlador(List<NotaSeguimiento> listaNotas, List<Llamada> listaLlamadas, List<Operador> listaOperadores) {
        this.vista = new NotaVista();
        this.listaNotas = listaNotas;
        this.listaLlamadas = listaLlamadas;
        this.listaOperadores = listaOperadores;
    }

    public void iniciarMenuNotas() {
        String codigoBuscado = vista.pedirCodigoLlamada();
        Llamada llamadaEncontrada = buscarLlamadaPorCodigo(codigoBuscado);

        if (llamadaEncontrada == null) {
            vista.mostrarMensajeError("No se encontró ninguna llamada con el código " + codigoBuscado);
            return;
        }

        boolean regresar = false;
        while (!regresar) {
            List<NotaSeguimiento> notasDeEstaLlamada = obtenerNotasPorLlamada(llamadaEncontrada);
            vista.mostrarNotasDeLlamada(llamadaEncontrada.getCodigo(), notasDeEstaLlamada);

            int opcion = vista.mostrarSubMenuNotas();

            switch (opcion) {
                case 1:
                    agregarNuevaNota(llamadaEncontrada);
                    break;
                case 2:
                    regresar = true;
                    break;
                default:
                    vista.mostrarMensajeError("Opción fuera de rango. Ingrese 1 o 2.");
            }
        }
    }

    private void agregarNuevaNota(Llamada llamada) {
        if (listaOperadores.isEmpty()) {
            vista.mostrarMensajeError("No hay operadores registrados en el sistema.");
            return;
        }

        Operador operadorSeleccionado = null;
        while (operadorSeleccionado == null) {
            int indiceOp = vista.pedirOperador(listaOperadores);
            if (indiceOp >= 1 && indiceOp <= listaOperadores.size()) {
                operadorSeleccionado = listaOperadores.get(indiceOp - 1);
            } else {
                vista.mostrarMensajeError("Número de operador inválido.");
            }
        }

        String fechaActualStr = RelojSistema.formatear(RelojSistema.ahora());
        String textoNota = "";
        boolean textoValido = false;

        while (!textoValido) {
            textoNota = vista.pedirTextoNota(fechaActualStr);
            if (Validador.noVacio(textoNota)) {
                textoValido = true;
            } else {
                vista.mostrarMensajeError("El texto de la nota no puede estar vacío.");
            }
        }

        NotaSeguimiento nuevaNota = new NotaSeguimiento(operadorSeleccionado, textoNota, llamada);
        listaNotas.add(nuevaNota);
        vista.mostrarExitoNota(nuevaNota);
    }

    private Llamada buscarLlamadaPorCodigo(String codigo) {
        for (Llamada ll : listaLlamadas) {
            if (ll.getCodigo().equalsIgnoreCase(codigo)) {
                return ll;
            }
        }
        return null;
    }

    private List<NotaSeguimiento> obtenerNotasPorLlamada(Llamada llamada) {
        List<NotaSeguimiento> resultado = new ArrayList<>();
        for (NotaSeguimiento nota : listaNotas) {
            if (nota.getLlamada().getCodigo().equals(llamada.getCodigo())) {
                resultado.add(nota);
            }
        }
        return resultado;
    }
}
