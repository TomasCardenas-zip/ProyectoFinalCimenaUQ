package ProyectoFinal.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private TarjetaVirtual tarjetaVirtual;
    private final List<RegistroPuntos> puntos;
    private final List<Compra> compras;

    public Cliente(String id, String nombre, String correo, String telefono, String numeroTarjeta) {
        super(id, nombre, correo, telefono);
        this.tarjetaVirtual = new TarjetaVirtual(numeroTarjeta, 0.0);
        this.puntos = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public int getPuntosValidosTotales() {
        return puntos.stream()
                .filter(p -> !p.estaVencido())
                .mapToInt(RegistroPuntos::getCantidad)
                .sum();
    }

    public TarjetaVirtual getTarjetaVirtual() { return tarjetaVirtual; }
    public void setTarjetaVirtual(TarjetaVirtual tarjetaVirtual) { this.tarjetaVirtual = tarjetaVirtual; }

    public List<RegistroPuntos> getPuntos() { return puntos; }
    public List<Compra> getCompras() { return compras; }

    public void agregarPuntos(RegistroPuntos registro) {
        this.puntos.add(registro);
    }

    public void agregarCompra(Compra compra) {
        this.compras.add(compra);
    }
}
