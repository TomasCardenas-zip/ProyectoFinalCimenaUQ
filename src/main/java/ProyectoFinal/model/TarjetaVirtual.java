package ProyectoFinal.model;

import ProyectoFinal.exception.SaldoInsuficienteException;

public class TarjetaVirtual {
    private final String numeroTarjeta;
    private double saldo;
    private boolean activa;

    public TarjetaVirtual(String numeroTarjeta, double saldoInicial) {
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = Math.max(0, saldoInicial);
        this.activa = true;
    }

    public void recargar(double monto) {
        if (!activa) {
            throw new IllegalStateException("La tarjeta virtual se encuentra inactiva.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a recargar debe ser mayor a cero.");
        }
        this.saldo += monto;
    }


    public void descontar(double monto) throws SaldoInsuficienteException {
        if (!activa) {
            throw new IllegalStateException("La tarjeta virtual se encuentra inactiva.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a descontar debe ser positivo.");
        }
        if (this.saldo < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la tarjeta virtual. Saldo disponible: $" + this.saldo);
        }
        this.saldo -= monto;
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}
