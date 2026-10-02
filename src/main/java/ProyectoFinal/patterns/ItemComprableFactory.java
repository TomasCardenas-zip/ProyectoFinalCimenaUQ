package ProyectoFinal.patterns;


import ProyectoFinal.model.ItemComprable;

public abstract class ItemComprableFactory {
        public abstract ItemComprable crearItem(String id, String nombre, double precio, Object extra);
    }

