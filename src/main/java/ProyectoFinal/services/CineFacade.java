package ProyectoFinal.services;

import ProyectoFinal.exception.*;
import ProyectoFinal.model.*;
import ProyectoFinal.model.enums.EstadoAsiento;
import ProyectoFinal.patterns.IPasarelaPago;
import ProyectoFinal.patterns.PasarelaPagoAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class CineFacade {

    private static CineFacade instancia;

    private final CinemaUQEngine engine;
    private final AutenticacionService autenticacionService;
    private final ReservaService reservaService;
    private final PagoService pagoService;
    private final IPasarelaPago pasarelaPago;

    private CineFacade() {
        this.engine = CinemaUQEngine.getInstancia();
        this.autenticacionService = engine.getAutenticacionService();
        this.reservaService = engine.getReservaService();
        this.pagoService = engine.getPagoService();
        // Usamos el adaptador para integrarnos con la pasarela bancaria externa
        this.pasarelaPago = new PasarelaPagoAdapter();
    }

    public static synchronized CineFacade getInstancia() {
        if (instancia == null) {
            instancia = new CineFacade();
        }
        return instancia;
    }

    // ==========================================
    // MÓDULO DE AUTENTICACIÓN
    // ==========================================

    public boolean autenticar(String correo) {
        return autenticacionService.autenticarUsuario(engine.getPersonas(), correo);
    }

    public Persona getUsuarioAutenticado() {
        return autenticacionService.getUsuarioAutenticado();
    }

    public void cerrarSesion() {
        autenticacionService.cerrarSesion();
    }

    // ==========================================
    // MÓDULO DE CARTELERA Y RESERVAS
    // ==========================================

    public List<Funcion> getFuncionesDisponibles() {
        return engine.getFunciones();
    }

    public Funcion buscarFuncionPorId(String idFuncion) {
        return reservaService.buscarFuncionPorId(engine.getFunciones(), idFuncion);
    }

    public void validarAsientosDisponibles(List<AsientoFuncion> asientos) throws AsientoOcupadoException, ReservaInvalidaException {
        if (asientos == null || asientos.isEmpty()) {
            throw new ReservaInvalidaException("Debe seleccionar al menos un asiento para continuar.");
        }

        if (asientos.size() > 8) {
            throw new ReservaInvalidaException("Regla RN-010: No puede adquirir más de 8 asientos en una única compra.");
        }

        for (AsientoFuncion af : asientos) {
            if (af.isOcupado() || af.getEstado() == EstadoAsiento.OCUPADO) {
                throw new AsientoOcupadoException("El asiento " + af.getAsiento().getCodigo() + " ya se encuentra ocupado.");
            }
        }
    }

    // ==========================================
    // MÓDULO DE PROCESAMIENTO DE COMPRA (FACHADA)
    // ==========================================

    public Compra realizarCompraGlobal(Cliente cliente, Funcion funcion, List<AsientoFuncion> asientosSeleccionados, List<ItemComprable> confiteriaYAdiciones)
            throws CinemaUQException {

        if (cliente == null) {
            throw new ReservaInvalidaException("Se requiere un cliente válido para procesar la compra.");
        }

        // 1. Validar disponibilidad de los asientos
        validarAsientosDisponibles(asientosSeleccionados);

        // 2. Construir la compra mediante CompraBuilder
        CompraBuilder builder = new CompraBuilder();
        builder.setCliente(cliente);

        // Crear entradas a través de la fábrica / entradas decoradas e incluirlas
        for (AsientoFuncion af : asientosSeleccionados) {
            Entrada entrada = new Entrada("ENT-" + System.currentTimeMillis() + "-" + af.getAsiento().getCodigo(), funcion, af);
            builder.agregarItem(entrada);
        }

        // Agregar ítems de confitería o combos (Composite) o adiciones (Decorator)
        if (confiteriaYAdiciones != null) {
            for (ItemComprable item : confiteriaYAdiciones) {
                builder.agregarItem(item);
            }
        }

        Compra compra = builder.build();

        // 3. Validar cobro bancario vía PasarelaPagoAdapter y descontar del saldo de la tarjeta virtual
        pasarelaPago.cobrar(cliente.getTarjetaVirtual().getNumeroTarjeta(), compra.getTotal());
        pagoService.procesarCompra(compra);

        // 4. Marcar asientos como ocupados en la función
        for (AsientoFuncion af : asientosSeleccionados) {
            reservaService.ocuparAsiento(af);
        }

        // 5. Acumular puntos de lealtad (RN-009: 10 puntos por cada $1.000)
        if (cliente.getRegistroPuntos() != null) {
            cliente.getRegistroPuntos().acumularPuntos(compra.getTotal());
        }

        // 6. Registrar en el historial global
        engine.getCompras().add(compra);

        return compra;
    }

    // ==========================================
    // MÓDULO FINANCIERO Y REEMBOLSOS
    // ==========================================

    public void recargarSaldoTarjeta(Cliente cliente, double monto) throws TarjetaInactivaException {
        if (cliente == null || cliente.getTarjetaVirtual() == null) {
            throw new IllegalArgumentException("El cliente no posee una tarjeta virtual asignada.");
        }
        if (!cliente.getTarjetaVirtual().isActiva()) {
            throw new TarjetaInactivaException("No se puede recargar. La tarjeta virtual se encuentra inactiva.");
        }
        cliente.getTarjetaVirtual().recargar(monto);
    }

    public boolean cancelarYReembolsarCompra(String idCompra) {
        return engine.cancelarYReembolsarCompra(idCompra);
    }

    public List<Compra> getHistorialComprasCliente(Cliente cliente) {
        if (cliente == null) return List.of();
        return engine.getCompras().stream()
                .filter(c -> c.getCliente().getId().equalsIgnoreCase(cliente.getId()))
                .collect(Collectors.toList());
    }

    // Getters de soporte para controladores
    public CinemaUQEngine getEngine() {
        return engine;
    }
}