package ProyectoFinal.model;

public class Asiento {
    private int fila;
    private int columna;
    private boolean esAccesible;

    public Asiento(int fila, int columna, boolean esAccesible) {
        this.fila = fila;
        this.columna = columna;
        this.esAccesible = esAccesible;
    }

    public String getCodigo() {
        char letraFila = (char) ('A' + fila - 1);
        return "" + letraFila + columna;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public boolean isEsAccesible() { return esAccesible; }
}

