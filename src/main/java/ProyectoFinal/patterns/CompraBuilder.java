package ProyectoFinal.patterns;

import ProyectoFinal.model.Cliente;
import ProyectoFinal.model.Compra;
import ProyectoFinal.model.ItemComprable;
import ProyectoFinal.model.enums.EstadoCompra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompraBuilder {
    private String idCompra;
    private Cliente cliente;
    private final List<ItemComprable> items = new ArrayList<>();
    private LocalDateTime fecha = LocalDateTime.now();

    public CompraBuilder setIdCompra(String idCompra) {
        this.idCompra = idCompra;
        return this;
    }

    public CompraBuilder setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public CompraBuilder agregarItem(ItemComprable item) {
        this.items.add(item);
        return this;
    }

    public CompraBuilder setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public Compra build() {
        if (cliente == null) {
            throw new IllegalStateException("No se puede crear una compra sin cliente asignado.");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("La compra debe incluir al menos un item.");
        }

        double total = items.stream().mapToDouble(ItemComprable::getPrecioFinal).sum();
        return new Compra(idCompra, cliente, items, fecha, total, EstadoCompra.CONFIRMADA);
    }
}
