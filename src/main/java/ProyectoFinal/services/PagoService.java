package ProyectoFinal.services;

import ProyectoFinal.exception.SaldoInsuficienteException;
import ProyectoFinal.model.Compra;
import ProyectoFinal.model.Entrada;
import ProyectoFinal.model.ItemComprable;
import ProyectoFinal.model.TarjetaVirtual;

public class PagoService {

    public void procesarCompra(Compra compra) throws SaldoInsuficienteException {
        TarjetaVirtual tarjeta = compra.getCliente().getTarjetaVirtual();
        if (tarjeta == null) {
            throw new IllegalStateException("El cliente no tiene asignada una tarjeta virtual.");
        }

        // 1. Descuenta el saldo (lanza SaldoInsuficienteException si el saldo no alcanza)
        tarjeta.descontar(compra.getTotal());

        // 2. Confirma el estado de la compra tras el cobro exitoso
        compra.confirmarCompra();

        // 3. Ocupa formalmente las sillas asociadas a las entradas compradas
        for (ItemComprable item : compra.getItems()) {
            if (item instanceof Entrada entrada) {
                entrada.getAsientoFuncion().setEstado(ProyectoFinal.model.enums.EstadoAsiento.OCUPADO);
            }
        }
    }
}
