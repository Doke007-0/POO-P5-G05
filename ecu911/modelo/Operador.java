package ecu911.modelo;

import java.util.Objects;


public class Operador extends Persona {

    /** Contador compartido por todos los operadores para generar el código. */
    private static int contador = 0;

    private final String codigo;
    private Turno turno;
    private String email;

   
    public Operador(String nombres, String apellidos, Turno turno, String email) {
        super(nombres, apellidos);
        contador++;
        this.codigo = String.format("OP-%02d", contador);
        this.turno = turno;
        this.email = email == null ? "" : email.trim();
    }

    public String getCodigo() {
        return codigo;
    }

  
    public Turno getTurno() {
        return turno;
    }

   
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

  
    public String getEmail() {
        return email;
    }

  
    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    @Override
    public String getIdentificador() {
        return codigo;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Operador)) {
            return false;
        }
        return codigo.equals(((Operador) otro).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
