package ProyectoFinal.patterns;

import ProyectoFinal.model.ItemComprable;

public abstract class EntradaDecorator extends ItemComprable {

    protected final ItemComprable itemDecorado;

    public EntradaDecorator(ItemComprable itemDecorado) {
        super(itemDecorado.getId(), itemDecorado.getNombre(), itemDecorado.getPrecioBase());
        this.itemDecorado = itemDecorado;
    }

    public ItemComprable getItemDecorado() {
        return itemDecorado;
    }
}