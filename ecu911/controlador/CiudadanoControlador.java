package ecu911.controlador;

import ecu911.modelo.Ciudadano;
import ecu911.modelo.Llamada;
import ecu911.util.Validador;
import ecu911.vista.CiudadanoVista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class CiudadanoControlador {

    private final CiudadanoVista vista;
    private final List<Ciudadano> listaCiudadanos;


    public CiudadanoControlador(Scanner scanner, List<Ciudadano> listaCiudadanos) {
        this.vista = new CiudadanoVista(scanner);
        this.listaCiudadanos = listaCiudadanos;
    }

//menu de administracion aca
    public void iniciarMenuCiudadanos() {
        boolean regresar = false;

        while (!regresar) {
            vista.mostrarCiudadanos(listaCiudadanos);

            int opcion = vista.mostrarMenuCiudadanos();

            switch (opcion) {
                case 1:
                    consultarHistorial();
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
//pide y valida la cedula, bsuca al ciudadano y 
    //muestra el historial
//importante: si ese ingresa enter sin escribir nada, vuelve al listado
    private void consultarHistorial() {
        if (listaCiudadanos.isEmpty()) {
            vista.mostrarMensajeError("No existen ciudadanos registrados para consultar.");
            vista.esperarEnter();
            return;
        }

        vista.mostrarEncabezadoConsulta();

        Ciudadano ciudadano = null;
        while (ciudadano == null) {
            String cedula = vista.pedirCedula();

            if (cedula.isEmpty()) {
                return;
            }

            if (!Validador.esCedulaValida(cedula)) {
                vista.mostrarMensajeError(
                        "La cédula debe contener exactamente 10 dígitos numéricos. "
                        + "Intente nuevamente.");
                continue;
            }

            ciudadano = buscarCiudadanoPorCedula(cedula);

            if (ciudadano == null) {
                vista.mostrarMensajeError(
                        "No se encontró ningún ciudadano con la cédula "
                        + cedula + ". Intente nuevamente.");
            }
        }

        List<Llamada> historial = new ArrayList<>(ciudadano.getLlamadas());
        Collections.reverse(historial);

        vista.mostrarHistorial(ciudadano, historial);
        vista.esperarEnter();
    }

//busca ciudadano por la cedula (lo dice el nombre del metodo)
    private Ciudadano buscarCiudadanoPorCedula(String cedula) {
        for (Ciudadano ciudadano : listaCiudadanos) {
            if (ciudadano.getCedula().equals(cedula)) {
                return ciudadano;
            }
        }
        return null;
    }
}
