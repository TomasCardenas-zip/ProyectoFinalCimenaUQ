package ProyectoFinal.model;

import java.time.LocalDate;

public class RegistroPuntos {
    private int cantidad;
    private LocalDate fechaObtencion;
    private LocalDate fechaVencimiento;

    public RegistroPuntos(int cantidad, LocalDate fechaObtencion) {
        this.cantidad = cantidad;
        this.fechaObtencion = fechaObtencion;
        this.fechaVencimiento = fechaObtencion.plusYears(1);
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public LocalDate getFechaObtencion() { return fechaObtencion; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
}
