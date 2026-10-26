package ProyectoFinal.model;

import java.time.LocalDateTime;
import java.util.List;
import ProyectoFinal.model.enums.EstadoCompra;
import java.util.ArrayList;
import java.util.Collections;

public class Compra {
    private final String idCompra;
    private final Cliente cliente;
    private final List<ItemComprable> items;
    private final LocalDateTime fecha;
    private double total;
    private EstadoCompra estado;
    private Reembolso reembolso;

    // Constructor Privado: mi correccion

    private Compra(CompraBuilder builder) {
        this.idCompra = builder.idCompra;
        this.cliente = builder.cliente;
        this.items = new ArrayList<>(builder.items);
        this.fecha = LocalDateTime.now();
        this.estado = EstadoCompra.PENDIENTE; // Nace PENDIENTE, no CONFIRMADA
        this.calcularTotal();
    }

    public void calcularTotal() {
        this.total = items.stream().mapToDouble(ItemComprable::getPrecioFinal).sum();
    }

    public void confirmarCompra() {
        this.estado = EstadoCompra.CONFIRMADA;
    }

    public String getIdCompra() { return idCompra; }
    public Cliente getCliente() { return cliente; }
    public List<ItemComprable> getItems() { return Collections.unmodifiableList(items); }
    public LocalDateTime getFecha() { return fecha; }
    public double getTotal() { return total; }
    public EstadoCompra getEstado() { return estado; }
    public Reembolso getReembolso() { return reembolso; }

    public void setEstado(EstadoCompra estado) { this.estado = estado; }
    public void setReembolso(Reembolso reembolso) { this.reembolso = reembolso; }


    public static class CompraBuilder {
        private String idCompra;
        private Cliente cliente;
        private final List<ItemComprable> items = new ArrayList<>();

        public CompraBuilder setIdCompra(String idCompra) {
            this.idCompra = idCompra;
            return this;
        }

        public CompraBuilder setCliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public CompraBuilder agregarItem(ItemComprable item) {
            if (item != null) {
                this.items.add(item);
            }
            return this;
        }

        public Compra build() {
            // Validaciones estrictas antes de instanciar
            if (cliente == null) {
                throw new IllegalStateException("No se puede construir una compra sin asignarle un cliente.");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("La compra debe incluir al menos un ítem.");
            }
            if (idCompra == null || idCompra.isBlank()) {
                this.idCompra = "COMP-" + System.currentTimeMillis();
            }

            // Invoca el constructor privado de Compra
            return new Compra(this);
        }
    }
}