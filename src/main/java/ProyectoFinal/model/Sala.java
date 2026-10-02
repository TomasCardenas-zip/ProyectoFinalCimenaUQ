package ProyectoFinal.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String numeroSala;
    private int filas;
    private int columnas;
    private final List<Asiento> asientos;

    public Sala(String numeroSala, int filas, int columnas) {
        this.numeroSala = numeroSala;
        this.filas = filas;
        this.columnas = columnas;
        this.asientos = new ArrayList<>();
        generarAsientos();
    }

    private void generarAsientos() {
        for (int f = 1; f <= filas; f++) {
            for (int c = 1; c <= columnas; c++) {
                boolean accesible = (f == 1);
                asientos.add(new Asiento(f, c, accesible));
            }
        }
    }

    public String getNumeroSala() { return numeroSala; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public List<Asiento> getAsientos() { return asientos; }
}
