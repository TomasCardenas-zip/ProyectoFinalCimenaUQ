package ProyectoFinal.model;

import ProyectoFinal.model.enums.EstadoAsiento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {
    private String id;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDateTime fechaHora;
    private double precioEntrada;
    private final List<AsientoFuncion> asientosFuncion;

    public Funcion(String id, Pelicula pelicula, Sala sala, LocalDateTime fechaHora, double precioEntrada) {
        this.id = id;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.precioEntrada = precioEntrada;
        this.asientosFuncion = new ArrayList<>();
        inicializarAsientos();
    }

    private void inicializarAsientos() {
        for (Asiento asiento : sala.getAsientos()) {
            asientosFuncion.add(new AsientoFuncion(asiento));
        }
    }

    public boolean estaDisponible(String codigoAsiento) {
        return asientosFuncion.stream()
                .filter(af -> af.getAsiento().getCodigo().equalsIgnoreCase(codigoAsiento))
                .anyMatch(af -> af.getEstado() == EstadoAsiento.DISPONIBLE || af.getEstado() == EstadoAsiento.ACCESIBLE);
    }

    public void ocuparAsiento(String codigoAsiento) {
        asientosFuncion.stream()
                .filter(af -> af.getAsiento().getCodigo().equalsIgnoreCase(codigoAsiento))
                .findFirst()
                .ifPresent(af -> af.setEstado(EstadoAsiento.OCUPADO));
    }

    public void liberarAsiento(String codigoAsiento) {
        asientosFuncion.stream()
                .filter(af -> af.getAsiento().getCodigo().equalsIgnoreCase(codigoAsiento))
                .findFirst()
                .ifPresent(af -> af.setEstado(af.getAsiento().isEsAccesible() ? EstadoAsiento.ACCESIBLE : EstadoAsiento.DISPONIBLE));
    }

    public String getId() { return id; }
    public Pelicula getPelicula() { return pelicula; }
    public Sala getSala() { return sala; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getPrecioEntrada() { return precioEntrada; }
    public List<AsientoFuncion> getAsientosFuncion() { return asientosFuncion; }
}
