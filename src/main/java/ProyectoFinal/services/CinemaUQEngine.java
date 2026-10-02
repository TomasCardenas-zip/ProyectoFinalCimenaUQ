package ProyectoFinal.services;

import ProyectoFinal.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CinemaUQEngine {
    private static CinemaUQEngine instancia;

    private final List<Cliente> clientes;
    private final List<Administrador> administradores;
    private final List<Pelicula> peliculas;
    private final List<Sala> salas;
    private final List<Funcion> funciones;
    private final List<Compra> compras;

    private Persona usuarioAutenticado;

    private CinemaUQEngine() {
        this.clientes = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.compras = new ArrayList<>();

        cargarDatosSemilla();
    }

    public static synchronized CinemaUQEngine getInstancia() {
        if (instancia == null) {
            instancia = new CinemaUQEngine();
        }
        return instancia;
    }

    private void cargarDatosSemilla() {
        Administrador admin = new Administrador("ADM01", "Admin General", "admin@cinemauq.com", "3000000000", "EMP-101");
        administradores.add(admin);

        Cliente cliente = new Cliente("CLI01", "Juan Perez", "juan@gmail.com", "3111111111", "TV-1001");
        cliente.getTarjetaVirtual().recargar(50000.0);
        clientes.add(cliente);

        Pelicula pelicula = new Pelicula("PEL01", "Avatar 3", 190, "Ciencia Ficcion");
        peliculas.add(pelicula);

        Sala sala = new Sala("SALA-1", 5, 6);
        salas.add(sala);

        Funcion funcion = new Funcion("FUN01", pelicula, sala, LocalDateTime.now().plusDays(1), 15000.0);
        funciones.add(funcion);
    }

    public List<Cliente> getClientes() { return clientes; }
    public List<Administrador> getAdministradores() { return administradores; }
    public List<Pelicula> getPeliculas() { return peliculas; }
    public List<Sala> getSalas() { return salas; }
    public List<Funcion> getFunciones() { return funciones; }
    public List<Compra> getCompras() { return compras; }

    public Persona getUsuarioAutenticado() { return usuarioAutenticado; }
    public void setUsuarioAutenticado(Persona usuario) { this.usuarioAutenticado = usuario; }
    public void cerrarSesion() { this.usuarioAutenticado = null; }
}
