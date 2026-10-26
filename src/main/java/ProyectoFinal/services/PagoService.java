package ProyectoFinal.services;

import ProyectoFinal.exception.SaldoInsuficienteException;
import ProyectoFinal.model.Compra;
import ProyectoFinal.model.Entrada;
import ProyectoFinal.model.ItemComprable;
import ProyectoFinal.model.TarjetaVirtual;
import ProyectoFinal.model.enums.EstadoAsiento;

public class PagoService {

    public void procesarCompra(Compra compra) throws SaldoInsuficienteException {
        if (compra == null || compra.getCliente() == null) {
            throw new IllegalArgumentException("La compra o el cliente asociado no pueden ser nulos.");
        }

        TarjetaVirtual tarjeta = compra.getCliente().getTarjetaVirtual();
        if (tarjeta == null) {
            throw new IllegalStateException("El cliente no tiene asignada una tarjeta virtual.");
        }

        // 1. Descuenta del saldo de la TarjetaVirtual (lanza SaldoInsuficienteException si no alcanza)
        tarjeta.descontar(compra.getTotal());

        // 2. Confirma la compra
        compra.confirmarCompra();

        // 3. Ocupa formalmente los asientos
        for (ItemComprable item : compra.getItems()) {
            if (item instanceof Entrada entrada && entrada.getAsientoFuncion() != null) {
                entrada.getAsientoFuncion().setEstado(EstadoAsiento.OCUPADO);
            }
        }
    }
}