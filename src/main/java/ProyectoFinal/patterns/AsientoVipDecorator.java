package ProyectoFinal.patterns;

import ProyectoFinal.model.ItemComprable;

public class AsientoVipDecorator extends EntradaDecorator {

    private final double recargoVip;

    public AsientoVipDecorator(ItemComprable itemDecorado, double recargoVip) {
        super(itemDecorado);
        this.recargoVip = Math.max(0, recargoVip);
    }

    @Override
    public double getPrecioFinal() {
        return itemDecorado.getPrecioFinal() + recargoVip;
    }

    @Override
    public String getNombre() {
        return itemDecorado.getNombre() + " [Ubicación VIP]";
    }
}