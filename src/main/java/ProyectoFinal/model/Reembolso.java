package ProyectoFinal.model;

import java.time.LocalDateTime;

public class Reembolso {
    private String idReembolso;
    private LocalDateTime fecha;
    private double montoDevuelto;
    private double porcentajeAplicado;

    public Reembolso(String idReembolso, LocalDateTime fecha, double montoDevuelto, double porcentajeAplicado) {
        this.idReembolso = idReembolso;
        this.fecha = fecha;
        this.montoDevuelto = montoDevuelto;
        this.porcentajeAplicado = porcentajeAplicado;
    }

    public String getIdReembolso() { return idReembolso; }
    public LocalDateTime getFecha() { return fecha; }
    public double getMontoDevuelto() { return montoDevuelto; }
    public double getPorcentajeAplicado() { return porcentajeAplicado; }
}
