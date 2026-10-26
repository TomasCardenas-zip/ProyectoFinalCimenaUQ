package ProyectoFinal.patterns;

import ProyectoFinal.model.ComboConfiteria;
import ProyectoFinal.model.ProductoConfiteria;

public class ConfiteriaFactory {

    public ProductoConfiteria crearProducto(String id, String nombre, double precio, String categoria) {
        return new ProductoConfiteria(id, nombre, precio, categoria);
    }

    public ComboConfiteria crearCombo(String id, String nombre, double precioBase, double porcentajeDescuento) {
        return new ComboConfiteria(id, nombre, precioBase, porcentajeDescuento);
    }
}