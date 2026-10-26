package ProyectoFinal.services;

import ProyectoFinal.exception.CinemaUQException;
import ProyectoFinal.model.*;
import java.util.List;

public class CineFacade {

    private static CineFacade instancia;
    private final CinemaUQEngine engine;

    private CineFacade() {
        this.engine = CinemaUQEngine.getInstancia();
    }

    public static synchronized CineFacade getInstancia() {
        if (instancia == null) {
            instancia = new CineFacade();
        }
        return instancia;
    }

    public boolean autenticar(String correo) {
        return engine.autenticarUsuario(correo);
    }

    public Persona getUsuarioAutenticado() {
        return engine.getUsuarioAutenticado();
    }

    public void cerrarSesion() {
        engine.cerrarSesion();
    }

    public List<Funcion> getFuncionesDisponibles() {
        return engine.getFunciones();
    }

    public List<ProductoConfiteria> getProductosConfiteria() {
        return engine.getProductosConfiteria();
    }

    public Compra realizarCompraGlobal(Cliente cliente, Funcion funcion, List<AsientoFuncion> asientos, List<ProductoConfiteria> snacks) throws CinemaUQException {
        if (cliente == null) {
            throw new CinemaUQException("Debe haber un cliente autenticado para realizar la compra.");
        }
        if (asientos == null || asientos.isEmpty()) {
            throw new CinemaUQException("Debe seleccionar al menos un asiento.");
        }

        double totalEntradas = asientos.size() * funcion.getPrecioEntrada();
        double totalSnacks = 0.0;

        if (snacks != null) {
            totalSnacks = snacks.stream().mapToDouble(ProductoConfiteria::getPrecio).sum();
        }

        double totalGeneral = totalEntradas + totalSnacks;

        if (cliente.getTarjetaVirtual().getSaldo() < totalGeneral) {
            throw new CinemaUQException("Saldo insuficiente en Tarjeta Virtual. Requiere $" + totalGeneral + " pero posee $" + cliente.getTarjetaVirtual().getSaldo());
        }

        cliente.getTarjetaVirtual().descontar(totalGeneral);
        int puntosGanados = (int) (totalGeneral / 1000 * 10);
        cliente.getRegistroPuntos().acumularPuntos(puntosGanados);

        Compra compra = new Compra.CompraBuilder()
                .setId("CMP-" + System.currentTimeMillis())
                .setCliente(cliente)
                .setFuncion(funcion)
                .setAsientos(asientos)
                .setProductosConfiteria(snacks)
                .setTotal(totalGeneral)
                .build();

        cliente.getHistorialCompras().add(compra);
        return compra;
    }
}