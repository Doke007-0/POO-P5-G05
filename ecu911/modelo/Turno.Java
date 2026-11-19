package ecu911.modelo;

/**
 * Turnos de trabajo de un operador.
 */
public enum Turno {
    MATUTINO("Matutino"),

    VESPERTINO("Vespertino"),

    NOCTURNO("Nocturno");

    private final String etiqueta;

    Turno(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}