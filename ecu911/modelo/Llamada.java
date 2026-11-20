package ecu911.modelo;

import ecu911.util.RelojSistema;
import java.time.LocalDateTime;

public class Llamada {
    private static int contadorLlamadas = 1;
    private String codigo;
    private LocalDateTime fechaHora;
    private Operador operador;
    private Ciudadano ciudadano;
    private TipoEmergencia tipoEmergencia; // Usa el enum TipoEmergencia de tu grupo
    private String ciudad;
    private String barrio;
    private String detalle;
    private String estado;

    public Llamada(Operador operador, Ciudadano ciudadano, TipoEmergencia tipoEmergencia, String ciudad, String barrio, String detalle) {
        this.codigo = String.format("LL-%03d", contadorLlamadas++);
        this.fechaHora = RelojSistema.ahora();
        this.operador = operador;
        this.ciudadano = ciudadano;
        this.tipoEmergencia = tipoEmergencia;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.detalle = detalle;
        this.estado = "Registrada";
    }

    public String getCodigo() { return codigo; }
    public LocalDateTime getFechaHora() { return fechaHora; }//obitene la fecha de cuando se registro la llamada
    public Operador getOperador() { return operador; }
    public Ciudadano getCiudadano() { return ciudadano; }
    public TipoEmergencia getTipoEmergencia() { return tipoEmergencia; }
    public String getCiudad() { return ciudad; }
    public String getBarrio() { return barrio; }
    public String getDetalle() { return detalle; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}