package ecu911.controlador;

import ecu911.excepcion.DatoInvalidoException;
import ecu911.excepcion.LlamadaNoEncontradaException;
import ecu911.modelo.Llamada;
import ecu911.modelo.NotaSeguimiento;
import ecu911.modelo.Operador;
import ecu911.util.RelojSistema;
import ecu911.util.Validador;
import ecu911.vista.NotaVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador que maneja las operaciones del Menú 4 (Notas de seguimiento).
 *
 * @author Alfonso Beltran
 */
public class NotaControlador {
    private NotaVista vista;
    private List<NotaSeguimiento> listaNotas;
    private List<Llamada> listaLlamadas;
    private List<Operador> listaOperadores;

    //Constructor del controlador de notas.
     
    public NotaControlador(
            Scanner scanner,
            List<NotaSeguimiento> listaNotas,
            List<Llamada> listaLlamadas,
            List<Operador> listaOperadores) {
        this.vista = new NotaVista(scanner);
        this.listaNotas = listaNotas;
        this.listaLlamadas = listaLlamadas;
        this.listaOperadores = listaOperadores;
    }

    //Inicia el menú de administración de notas de seguimiento.
     
    public void iniciarMenuNotas() {
        String codigoBuscado = vista.pedirCodigoLlamada();
        Llamada llamadaEncontrada;

        try {
            llamadaEncontrada = buscarLlamadaPorCodigo(codigoBuscado);
        } catch (LlamadaNoEncontradaException e) {
            vista.mostrarMensajeError(e.getMessage());
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
                    break;
            }
        }
    }

    //Gestiona el proceso de registrar una nueva nota en una llamada específica.
     
    private void agregarNuevaNota(Llamada llamada) {
        if (listaOperadores.isEmpty()) {
            vista.mostrarMensajeError("No hay operadores registrados en el sistema.");
            return;
        }

        Operador operadorSeleccionado = null;
        while (operadorSeleccionado == null) {
            try {
                int indiceOp = vista.pedirOperador(listaOperadores);
                if (indiceOp < 1 || indiceOp > listaOperadores.size()) {
                    throw new DatoInvalidoException("Número de operador inválido. Seleccione una opción de la lista.");
                }
                operadorSeleccionado = listaOperadores.get(indiceOp - 1);
            } catch (DatoInvalidoException e) {
                vista.mostrarMensajeError(e.getMessage());
            }
        }

        String fechaActualStr = RelojSistema.formatear(RelojSistema.ahora());
        String textoNota = "";
        boolean textoValido = false;

        while (!textoValido) {
            try {
                textoNota = vista.pedirTextoNota(fechaActualStr);
                if (!Validador.noVacio(textoNota)) {
                    throw new DatoInvalidoException("El texto de la nota no puede estar vacío.");
                }
                textoValido = true;
            } catch (DatoInvalidoException e) {
                vista.mostrarMensajeError(e.getMessage());
            }
        }

        NotaSeguimiento nuevaNota = new NotaSeguimiento(operadorSeleccionado, textoNota, llamada);
        listaNotas.add(nuevaNota);
        vista.mostrarExitoNota(nuevaNota);
    }

    //Busca una llamada por su código o lanza una excepción si no existe.
     
    private Llamada buscarLlamadaPorCodigo(String codigo) throws LlamadaNoEncontradaException {
        for (Llamada ll : listaLlamadas) {
            if (ll.getCodigo().equalsIgnoreCase(codigo)) {
                return ll;
            }
        }
        throw new LlamadaNoEncontradaException("No se encontró ninguna llamada con el código " + codigo + ".");
    }

    //Filtra todas las notas registradas que pertenecen a una llamada.
     
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
