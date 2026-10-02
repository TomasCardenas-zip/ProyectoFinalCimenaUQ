package ProyectoFinal.model;

import ProyectoFinal.model.enums.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TarjetaVirtual {
    private String numeroTarjeta;
    private double saldo;
    private boolean activa;
    private final List<MovimientoTarjeta> historialMovimientos;

    public TarjetaVirtual(String numeroTarjeta, double saldoInicial) {
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = Math.max(0.0, saldoInicial);
        this.activa = true;
        this.historialMovimientos = new ArrayList<>();
    }

    public void recargar(double monto) {
        if (!activa || monto <= 0) return;
        this.saldo += monto;
        historialMovimientos.add(new MovimientoTarjeta(
                LocalDateTime.now(), TipoMovimiento.RECARGA, monto, "Recarga de saldo por Administrador"
        ));
    }

    public boolean descontar(double monto) {
        if (!activa || monto <= 0 || saldo < monto) {
            return false;
        }
        this.saldo -= monto;
        historialMovimientos.add(new MovimientoTarjeta(
                LocalDateTime.now(), TipoMovimiento.COMPRA, monto, "Pago de compra en CinemaUQ"
        ));
        return true;
    }

    public void acreditarReembolso(double monto) {
        if (!activa || monto <= 0) return;
        this.saldo += monto;
        historialMovimientos.add(new MovimientoTarjeta(
                LocalDateTime.now(), TipoMovimiento.REEMBOLSO, monto, "Acreditacion por reembolso"
        ));
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public List<MovimientoTarjeta> getHistorialMovimientos() { return historialMovimientos; }
}
