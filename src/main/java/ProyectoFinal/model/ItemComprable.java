package ProyectoFinal.model;

public abstract class ItemComprable {
    protected String id;
    protected String nombre;
    protected double precioBase;

    public ItemComprable(String id, String nombre, double precioBase) {
        this.id = id;
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public abstract double getPrecioFinal();

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecioBase() { return precioBase; }
}
