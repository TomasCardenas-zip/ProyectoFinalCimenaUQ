package ProyectoFinal.model;

public class Entrada extends ItemComprable{
    private String codigoAsiento;

    public Entrada(String id, String nombre, double precioBase, String codigoAsiento) {
        super(id, nombre, precioBase);
        this.codigoAsiento = codigoAsiento;
    }

    @Override
    public double getPrecioFinal() {
        return precioBase;
    }

    public String getCodigoAsiento() { return codigoAsiento; }
    public void setCodigoAsiento(String codigoAsiento) { this.codigoAsiento = codigoAsiento; }
}

