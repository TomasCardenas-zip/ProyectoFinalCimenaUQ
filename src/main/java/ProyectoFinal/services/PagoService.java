package ProyectoFinal.services;

import ProyectoFinal.exception.CinemaUQException;
import ProyectoFinal.model.Cliente;
import ProyectoFinal.model.Compra;

public class PagoService {

    public void procesarPago(Cliente cliente, Compra compra) throws CinemaUQException {
        if (cliente == null || cliente.getTarjetaVirtual() == null) {
            throw new CinemaUQException("El cliente o su tarjeta virtual no son válidos.");
        }

        if (compra == null) {
            throw new CinemaUQException("La información de la compra no es válida.");
        }

        double saldoDisponible = cliente.getTarjetaVirtual().getSaldo();
        if (saldoDisponible < compra.getTotal()) {
            throw new CinemaUQException("Saldo insuficiente en Tarjeta Virtual. Requiere $"
                    + compra.getTotal() + " pero posee $" + saldoDisponible);
        }

        // Descontar el total de la tarjeta virtual y acumular puntos
        cliente.getTarjetaVirtual().descontar(compra.getTotal());
        int puntosObtenidos = (int) (compra.getTotal() / 1000 * 10);
        cliente.getRegistroPuntos().acumularPuntos(puntosObtenidos);

        // Marcar la compra como confirmada
        compra.confirmarCompra();
    }
}