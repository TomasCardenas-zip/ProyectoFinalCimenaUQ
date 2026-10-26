package ProyectoFinal.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private TarjetaVirtual tarjetaVirtual;
    private RegistroPuntos registroPuntos;
    private List<Compra> historialCompras;

    public Cliente(String id, String nombre, String correo, String telefono, String numTarjeta) {
        super(id, nombre, correo, telefono);
        this.tarjetaVirtual = new TarjetaVirtual(numTarjeta, 0.0);
        this.registroPuntos = new RegistroPuntos();
        this.historialCompras = new ArrayList<>();
    }

    public TarjetaVirtual getTarjetaVirtual() {
        return tarjetaVirtual;
    }

    public void setTarjetaVirtual(TarjetaVirtual tarjetaVirtual) {
        this.tarjetaVirtual = tarjetaVirtual;
    }

    public RegistroPuntos getRegistroPuntos() {
        return registroPuntos;
    }

    public void setRegistroPuntos(RegistroPuntos registroPuntos) {
        this.registroPuntos = registroPuntos;
    }

    public List<Compra> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<Compra> historialCompras) {
        this.historialCompras = historialCompras;
    }
}