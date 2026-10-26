package ProyectoFinal.services;

import ProyectoFinal.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CinemaUQEngine {

    private static CinemaUQEngine instancia;

    private final List<Persona> personas = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Funcion> funciones = new ArrayList<>();
    private final List<ProductoConfiteria> productosConfiteria = new ArrayList<>();
    private Persona usuarioAutenticado;

    private CinemaUQEngine() {
        cargarDatosSemilla();
    }

    public static synchronized CinemaUQEngine getInstancia() {
        if (instancia == null) {
            instancia = new CinemaUQEngine();
        }
        return instancia;
    }

    private void cargarDatosSemilla() {
        // 1. Clientes Semilla
        Cliente clientePrueba = new Cliente("1001", "Carlos Pérez", "cliente@uniquindio.edu.co", "3001234567", "TJ-1001");
        clientePrueba.getTarjetaVirtual().recargar(100000.0);
        personas.add(clientePrueba);
        clientes.add(clientePrueba);

        // 2. Películas
        Pelicula p1 = new Pelicula("PEL-01", "Avatar: El Camino del Agua", 190, "Ciencia Ficción");
        Pelicula p2 = new Pelicula("PEL-02", "Oppenheimer", 180, "Drama / Historia");
        Pelicula p3 = new Pelicula("PEL-03", "Spider-Man: Across the Spider-Verse", 140, "Animación / Acción");
        Pelicula p4 = new Pelicula("PEL-04", "Intensamente 2", 96, "Animación / Familia");

        // 3. Salas
        Sala salaGeneral1 = new Sala("SALA-01", 4, 5);
        Sala salaGeneral2 = new Sala("SALA-02", 5, 6);
        Sala salaVIP = new Sala("SALA-VIP", 3, 4);

        // 4. Funciones
        LocalDateTime hoy = LocalDateTime.now();

        funciones.add(new Funcion("FUNC-01", p1, salaGeneral1, hoy.plusHours(2), 15000.0));
        funciones.add(new Funcion("FUNC-02", p2, salaGeneral2, hoy.plusHours(4), 18000.0));
        funciones.add(new Funcion("FUNC-03", p3, salaGeneral1, hoy.plusDays(1).withHour(15), 14000.0));
        funciones.add(new Funcion("FUNC-04", p4, salaGeneral2, hoy.plusDays(1).withHour(17), 12000.0));
        funciones.add(new Funcion("FUNC-05", p1, salaVIP, hoy.plusDays(2).withHour(20), 25000.0));

        // 5. Confitería
        productosConfiteria.add(new ProductoConfiteria("CONF-01", "Crispetas Grandes", 18000.0, "Snacks"));
        productosConfiteria.add(new ProductoConfiteria("CONF-02", "Gaseosa 32oz", 10000.0, "Bebidas"));
        productosConfiteria.add(new ProductoConfiteria("CONF-03", "Combo Pareja (Crispetas + 2 Gaseosas)", 32000.0, "Combos"));
        productosConfiteria.add(new ProductoConfiteria("CONF-04", "Perro Caliente + Bebida", 22000.0, "Combos"));
        productosConfiteria.add(new ProductoConfiteria("CONF-05", "Chocolatina / Dulces", 6000.0, "Snacks"));
    }

    public boolean autenticarUsuario(String correo) {
        Optional<Persona> match = personas.stream().filter(p -> p.getCorreo().equalsIgnoreCase(correo)).findFirst();
        if (match.isPresent()) {
            this.usuarioAutenticado = match.get();
            return true;
        }
        return false;
    }

    public Persona getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void cerrarSesion() {
        this.usuarioAutenticado = null;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public List<ProductoConfiteria> getProductosConfiteria() {
        return productosConfiteria;
    }
}