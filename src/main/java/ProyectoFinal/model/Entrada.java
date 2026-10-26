package ProyectoFinal.model;

public class Entrada extends ItemComprable {
    private final Funcion funcion;
    private final AsientoFuncion asientoFuncion;

    public Entrada(String id, String nombre, double precio, Funcion funcion, AsientoFuncion asientoFuncion) {
        super(id, nombre, precio);
        this.funcion = funcion;
        this.asientoFuncion = asientoFuncion;
    }

    @Override
    public double getPrecioFinal() {
        return getPrecioBase();
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public AsientoFuncion getAsientoFuncion() {
        return asientoFuncion;
    }
}

