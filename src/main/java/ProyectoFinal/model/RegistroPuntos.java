package ProyectoFinal.model;

public class RegistroPuntos {
    private int puntosTotales;

    public RegistroPuntos() {
        this.puntosTotales = 0;
    }

    public RegistroPuntos(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public void acumularPuntos(int puntos) {
        if (puntos > 0) {
            this.puntosTotales += puntos;
        }
    }
}