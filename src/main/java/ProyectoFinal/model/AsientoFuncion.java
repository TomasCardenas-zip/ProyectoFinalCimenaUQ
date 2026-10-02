package ProyectoFinal.model;

import ProyectoFinal.model.enums.EstadoAsiento;

public class AsientoFuncion {
    private Asiento asiento;
    private EstadoAsiento estado;

    public AsientoFuncion(Asiento asiento) {
        this.asiento = asiento;
        this.estado = asiento.isEsAccesible() ? EstadoAsiento.ACCESIBLE : EstadoAsiento.DISPONIBLE;
    }

    public Asiento getAsiento() { return asiento; }
    public EstadoAsiento getEstado() { return estado; }
    public void setEstado(EstadoAsiento estado) { this.estado = estado; }
}
