package ecu911.modelo;


public abstract class Persona {

    private String nombres;
    private String apellidos;

    
    //Crea una persona.
    
    protected Persona(String nombres, String apellidos) {
        this.nombres = nombres.trim();
        this.apellidos = apellidos.trim();
    }

    //Identificador propio de cada tipo de persona: el código en un operador
     
    public abstract String getIdentificador();

    //Obtiene los nombres.
    
    public String getNombres() {
        return nombres;
    }

    //Cambia los nombres.
     
    public void setNombres(String nombres) {
        this.nombres = nombres.trim();
    }

    //Obtiene los apellidos.
    
    public String getApellidos() {
        return apellidos;
    }

    //Cambia los apellidos.
     
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos.trim();
    }

    //Nombre completo: todos los nombres y todos los apellidos.
     
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    //Nombre corto: primer nombre y primer apellido.
     
    public String getNombreCorto() {
        return primeraPalabra(nombres) + " " + primeraPalabra(apellidos);
    }

    //Primer nombre seguido de todos los apellidos.
     
    public String getPrimerNombreYApellidos() {
        return primeraPalabra(nombres) + " " + apellidos;
    }

    private static String primeraPalabra(String texto) {
        return texto.split("\\s+")[0];
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
}
