package ProyectoFinal.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String id;
    private Cliente cliente;
    private Funcion funcion;
    private List<AsientoFuncion> asientos;
    private List<ProductoConfiteria> productosConfiteria;
    private double total;
    private LocalDateTime fechaHora;
    private String estado;

    private Compra(CompraBuilder builder) {
        this.id = builder.id;
        this.cliente = builder.cliente;
        this.funcion = builder.funcion;
        this.asientos = builder.asientos != null ? builder.asientos : new ArrayList<>();
        this.productosConfiteria = builder.productosConfiteria != null ? builder.productosConfiteria : new ArrayList<>();
        this.total = builder.total;
        this.fechaHora = builder.fechaHora != null ? builder.fechaHora : LocalDateTime.now();
        this.estado = builder.estado != null ? builder.estado : "PENDIENTE";
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public List<AsientoFuncion> getAsientos() {
        return asientos;
    }

    public List<ProductoConfiteria> getProductosConfiteria() {
        return productosConfiteria;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void confirmarCompra() {
        this.estado = "CONFIRMADA";
    }

    public String getItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("Asientos: ").append(asientos.size());
        if (!productosConfiteria.isEmpty()) {
            sb.append(" | Confitería: ").append(productosConfiteria.size()).append(" producto(s)");
        }
        return sb.toString();
    }

    public static class CompraBuilder {
        private String id;
        private Cliente cliente;
        private Funcion funcion;
        private List<AsientoFuncion> asientos;
        private List<ProductoConfiteria> productosConfiteria;
        private double total;
        private LocalDateTime fechaHora;
        private String estado = "PENDIENTE";

        public CompraBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public CompraBuilder setCliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public CompraBuilder setFuncion(Funcion funcion) {
            this.funcion = funcion;
            return this;
        }

        public CompraBuilder setAsientos(List<AsientoFuncion> asientos) {
            this.asientos = asientos;
            return this;
        }

        public CompraBuilder setProductosConfiteria(List<ProductoConfiteria> productosConfiteria) {
            this.productosConfiteria = productosConfiteria;
            return this;
        }

        public CompraBuilder setTotal(double total) {
            this.total = total;
            return this;
        }

        public CompraBuilder setFechaHora(LocalDateTime fechaHora) {
            this.fechaHora = fechaHora;
            return this;
        }

        public CompraBuilder setEstado(String estado) {
            this.estado = estado;
            return this;
        }

        public Compra build() {
            return new Compra(this);
        }
    }
}