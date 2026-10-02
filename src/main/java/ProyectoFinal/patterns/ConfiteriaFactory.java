package ProyectoFinal.patterns;

import ProyectoFinal.model.ComboConfiteria;
import ProyectoFinal.model.ItemComprable;
import ProyectoFinal.model.ProductoConfiteria;

public class ConfiteriaFactory extends ItemComprableFactory {
    @Override
    public ItemComprable crearItem(String id, String nombre, double precio, Object extra) {
        if (extra instanceof Double) {
            double descuento = (Double) extra;
            return new ComboConfiteria(id, nombre, precio, descuento);
        }
        String categoria = (extra != null) ? extra.toString() : "General";
        return new ProductoConfiteria(id, nombre, precio, categoria);
    }
}
