package ProyectoFinal.model;

public class ComboConfiteria extends ItemComprable{
    private double porcentajeDescuento;

    public ComboConfiteria(String id, String nombre, double precioBase, double porcentajeDescuento) {
        super(id, nombre, precioBase);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double getPrecioFinal() {
        return precioBase * (1.0 - (porcentajeDescuento / 100.0));
    }

    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento; }
}
