package ecu911.modelo;

import ecu911.util.RelojSistema;
import java.time.LocalDateTime;

/**
 * Representa una nota de seguimiento asociada a una llamada de emergencia.
 */
public class NotaSeguimiento {
    private static int contadorNotas = 1;

    private String codigo;
    private LocalDateTime fechaHora;
    private Operador operador;
    private String texto;
    private Llamada llamada;

    public NotaSeguimiento(Operador operador, String texto, Llamada llamada) {
        this.codigo = String.format("NT-%03d", contadorNotas++);
        this.fechaHora = RelojSistema.ahora();
        this.operador = operador;
        this.texto = texto;
        this.llamada = llamada;
    }

    public String getFechaHoraFormateada() {
        return RelojSistema.formatear(this.fechaHora);
    }

    public String getCodigo() { return codigo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Operador getOperador() { return operador; }
    public void setOperador(Operador operador) { this.operador = operador; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Llamada getLlamada() { return llamada; }
    public void setLlamada(Llamada llamada) { this.llamada = llamada; }
}