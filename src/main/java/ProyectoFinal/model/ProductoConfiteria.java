package ProyectoFinal.model;

public class ProductoConfiteria extends ItemComprable{
    private String categoria;

    public ProductoConfiteria(String id, String nombre, double precioBase, String categoria) {
        super(id, nombre, precioBase);
        this.categoria = categoria;
    }

    @Override
    public double getPrecioFinal() {
        return precioBase;
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
