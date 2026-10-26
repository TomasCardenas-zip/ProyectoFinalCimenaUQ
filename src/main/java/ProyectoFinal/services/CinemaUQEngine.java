package ProyectoFinal.services;

import ProyectoFinal.exception.SaldoInsuficienteException;
import ProyectoFinal.model.*;

import java.util.ArrayList;
import java.util.List;

public class CinemaUQEngine {

    private static CinemaUQEngine instancia;

    // Listas en memoria
    private final List<Persona> personas = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Funcion> funciones = new ArrayList<>();
    private final List<Compra> compras = new ArrayList<>();

    // Servicios especializados
    private final AutenticacionService autenticacionService;
    private final ReservaService reservaService;
    private final PagoService pagoService;

    private CinemaUQEngine() {
        this.autenticacionService = new AutenticacionService();
        this.reservaService = new ReservaService();
        this.pagoService = new PagoService();
        cargarDatosSemilla();
    }

    public static synchronized CinemaUQEngine getInstancia() {
        if (instancia == null) {
            instancia = new CinemaUQEngine();
        }
        return instancia;
    }

    private void cargarDatosSemilla() {
        // Inicialización de salas, películas, funciones y usuarios de prueba
    }

    // Delegaciones a AutenticacionService
    public boolean autenticarUsuario(String correo) {
        return autenticacionService.autenticarUsuario(personas, correo);
    }

    public Persona getUsuarioAutenticado() {
        return autenticacionService.getUsuarioAutenticado();
    }

    public void cerrarSesion() {
        autenticacionService.cerrarSesion();
    }

    // Delegaciones a PagoService
    public void procesarCompra(Compra compra) throws SaldoInsuficienteException {
        pagoService.procesarCompra(compra);
        compras.add(compra);
    }

    public boolean cancelarYReembolsarCompra(String idCompra) {
        Compra compra = compras.stream()
                .filter(c -> c.getIdCompra().equalsIgnoreCase(idCompra))
                .findFirst()
                .orElse(null);

        if (compra != null && compra.getEstado() == ProyectoFinal.model.enums.EstadoCompra.CONFIRMADA) {
            compra.getCliente().getTarjetaVirtual().recargar(compra.getTotal());
            compra.setEstado(ProyectoFinal.model.enums.EstadoCompra.CANCELADA);
            return true;
        }
        return false;
    }

    // Getters de colecciones
    public List<Persona> getPersonas() { return personas; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Funcion> getFunciones() { return funciones; }
    public List<Compra> getCompras() { return compras; }

    public AutenticacionService getAutenticacionService() { return autenticacionService; }
    public ReservaService getReservaService() { return reservaService; }
    public PagoService getPagoService() { return pagoService; }
}