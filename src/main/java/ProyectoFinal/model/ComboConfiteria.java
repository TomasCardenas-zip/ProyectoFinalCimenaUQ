package ProyectoFinal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComboConfiteria extends ItemComprable {

    private final List<ItemComprable> items = new ArrayList<>();
    private final double porcentajeDescuento;

    public ComboConfiteria(String id, String nombre, double precioBase, double porcentajeDescuento) {
        super(id, nombre, precioBase);
        this.porcentajeDescuento = Math.max(0, Math.min(100, porcentajeDescuento));
    }

    public void agregarItem(ItemComprable item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public void removerItem(ItemComprable item) {
        this.items.remove(item);
    }

    public List<ItemComprable> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    @Override
    public double getPrecioFinal() {
        double subtotal = items.isEmpty() ? getPrecioBase() : items.stream().mapToDouble(ItemComprable::getPrecioFinal).sum();
        return subtotal * (1.0 - (porcentajeDescuento / 100.0));
    }
}