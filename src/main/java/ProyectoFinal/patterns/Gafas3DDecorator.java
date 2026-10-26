package ProyectoFinal.patterns;

import ProyectoFinal.model.ItemComprable;

public class Gafas3DDecorator extends EntradaDecorator {

    private final double precioGafas;

    public Gafas3DDecorator(ItemComprable itemDecorado, double precioGafas) {
        super(itemDecorado);
        this.precioGafas = Math.max(0, precioGafas);
    }

    @Override
    public double getPrecioFinal() {
        return itemDecorado.getPrecioFinal() + precioGafas;
    }

    @Override
    public String getNombre() {
        return itemDecorado.getNombre() + " + Gafas 3D";
    }
}