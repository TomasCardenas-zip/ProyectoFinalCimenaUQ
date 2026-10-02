package ProyectoFinal.model;

import ProyectoFinal.model.enums.EstadoCompra;

import java.time.LocalDateTime;
import java.util.List;

public class Compra {
    private String idCompra;
    private Cliente cliente;
    private List<ItemComprable> items;
    private LocalDateTime fecha;
    private double total;
    private EstadoCompra estado;
    private Reembolso reembolso;

    public Compra(String idCompra, Cliente cliente, List<ItemComprable> items, LocalDateTime fecha, double total, EstadoCompra estado) {
        this.idCompra = idCompra;
        this.cliente = cliente;
        this.items = items;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public String getIdCompra() { return idCompra; }
    public Cliente getCliente() { return cliente; }
    public List<ItemComprable> getItems() { return items; }
    public LocalDateTime getFecha() { return fecha; }
    public double getTotal() { return total; }
    public EstadoCompra getEstado() { return estado; }
    public void setEstado(EstadoCompra estado) { this.estado = estado; }

    public Reembolso getReembolso() { return reembolso; }
    public void setReembolso(Reembolso reembolso) { this.reembolso = reembolso; }
}
