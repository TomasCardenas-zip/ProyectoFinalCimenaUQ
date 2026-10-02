package ProyectoFinal.model;

import ProyectoFinal.model.enums.TipoMovimiento;
import java.time.LocalDateTime;

public class MovimientoTarjeta {
    private LocalDateTime fechaHora;
    private TipoMovimiento tipo;
    private double monto;
    private String descripcion;

    public MovimientoTarjeta(LocalDateTime fechaHora, TipoMovimiento tipo, double monto, String descripcion) {
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public TipoMovimiento getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getDescripcion() { return descripcion; }
}
