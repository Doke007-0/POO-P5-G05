package ecu911.modelo;

import ecu911.util.Validador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//Ciudadano que ha realizado una llamada de emergencia. Se identifica por su
 
public class Ciudadano extends Persona {

    private String cedula;
    private final List<Llamada> llamadas;
    
    //Crea un ciudadano.
     
    public Ciudadano(String nombres, String apellidos, String cedula) {
        super(nombres, apellidos);
        this.cedula = cedula;
        this.llamadas = new ArrayList<>();
    }

    //Obtiene la cédula.
     
    public String getCedula() {
        return cedula;
    }

    //Cambia la cédula.
     
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    //Obtiene el historial de llamadas
    public List<Llamada> getLlamadas() {
        return Collections.unmodifiableList(llamadas);
    }

    //Agrega llamada
    public void agregarLlamada(Llamada llamada) {
        if (llamada != null && !llamadas.contains(llamada)) {
            llamadas.add(llamada);
        }
    }

    //Cuenta las llamadas realizadas por el tamaño d la lista
    public int getTotalLlamadas() {
        return llamadas.size();
    }

    //Chequea si la cedula es valida
    public boolean esCedulaValida(String c) {
        return Validador.esCedulaValida(c);
    }

    @Override
    public String getIdentificador() {
        return cedula;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Ciudadano)) {
            return false;
        }
        return cedula.equals(((Ciudadano) otro).cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }
}
