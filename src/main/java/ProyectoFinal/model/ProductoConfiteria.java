package ProyectoFinal.model;

public class ProductoConfiteria {
    private String id;
    private String nombre;
    private double precio;
    private String detalle; // Categoría o ruta de imagen opcional

    // Constructor con 3 parámetros
    public ProductoConfiteria(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.detalle = "";
    }

    // Constructor con 4 parámetros (resuelve la incoherencia de argumentos)
    public ProductoConfiteria(String id, String nombre, double precio, String detalle) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.detalle = detalle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}