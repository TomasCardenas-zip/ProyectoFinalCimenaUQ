package ProyectoFinal.patterns;

import ProyectoFinal.model.Entrada;
import ProyectoFinal.model.ItemComprable;
import ProyectoFinal.patterns.ItemComprableFactory;


public class EntradaFactory extends ItemComprableFactory {
    @Override
    public ItemComprable crearItem(String id, String nombre, double precio, Object extra) {
        String codigoAsiento = (extra != null) ? extra.toString() : "A1";
        return new Entrada(id, nombre, precio, codigoAsiento);
    }
}