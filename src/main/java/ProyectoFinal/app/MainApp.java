package ProyectoFinal.app;

import ProyectoFinal.exception.SaldoInsuficienteException;
import ProyectoFinal.model.*;
import ProyectoFinal.patterns.ConfiteriaFactory;
import ProyectoFinal.patterns.EntradaFactory;
import ProyectoFinal.services.CinemaUQEngine;

import java.util.Scanner;

public class MainApp {

    private static final CinemaUQEngine engine = CinemaUQEngine.getInstancia();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE CINE - CINEMAUQ ===");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrar Cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> registrarCliente();
                case 0 -> System.out.println("¡Gracias por usar CinemaUQ!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void iniciarSesion() {
        System.out.print("\nIngrese correo registrado: ");
        String correo = scanner.nextLine();

        if (engine.autenticarUsuario(correo)) {
            Persona usuario = engine.getUsuarioAutenticado();
            System.out.println("Bienvenido, " + usuario.getNombre());

            if (usuario instanceof Cliente) {
                menuCliente((Cliente) usuario);
            } else if (usuario instanceof Administrador) {
                menuAdministrador((Administrador) usuario);
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void registrarCliente() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        String numTarjeta = "TV-" + (1000 + engine.getClientes().size() + 1);
        Cliente nuevoCliente = new Cliente(id, nombre, correo, telefono, numTarjeta);
        engine.getClientes().add(nuevoCliente);

        System.out.println("¡Cliente registrado con éxito! Tarjeta virtual asignada: " + numTarjeta);
    }

    private static void menuCliente(Cliente cliente) {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver Cartelera / Funciones");
            System.out.println("2. Comprar Entrada + Combo");
            System.out.println("3. Ver Saldo de Tarjeta y Puntos");
            System.out.println("4. Solicitar Reembolso");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> mostrarCartelera();
                case 2 -> realizarCompra(cliente);
                case 3 -> mostrarEstadoCuenta(cliente);
                case 4 -> procesarReembolsoCliente();
                case 0 -> engine.cerrarSesion();
            }
        } while (opcion != 0 && engine.getUsuarioAutenticado() != null);
    }

    private static void mostrarCartelera() {
        System.out.println("\n--- CARTELERA DE FUNCIONES ---");
        for (Funcion f : engine.getFunciones()) {
            System.out.println("[" + f.getId() + "] " + f.getPelicula().getTitulo() +
                    " | Sala: " + f.getSala().getNumeroSala() +
                    " | Fecha/Hora: " + f.getFechaHora() +
                    " | Precio: $" + f.getPrecioEntrada());
        }
    }

    private static void realizarCompra(Cliente cliente) {
        mostrarCartelera();
        System.out.print("Ingrese ID de la función: ");
        String idFuncion = scanner.nextLine();

        Funcion funcionSeleccionada = engine.getFunciones().stream()
                .filter(f -> f.getId().equalsIgnoreCase(idFuncion))
                .findFirst()
                .orElse(null);

        if (funcionSeleccionada == null) {
            System.out.println("Función no encontrada.");
            return;
        }

        System.out.print("Ingrese el código del asiento (ej. A1, A2): ");
        String codigoAsiento = scanner.nextLine();

        // Se busca el AsientoFuncion correspondiente dentro de la función seleccionada
        AsientoFuncion asientoFuncion = funcionSeleccionada.getAsientosFuncion().stream()
                .filter(af -> af.getAsiento().getCodigo().equalsIgnoreCase(codigoAsiento))
                .findFirst()
                .orElse(null);

        if (asientoFuncion == null) {
            System.out.println("Asiento no encontrado en esta función.");
            return;
        }

        // Aplicación de Patrones Creacionales
        EntradaFactory entradaFactory = new EntradaFactory();
        ConfiteriaFactory confiteriaFactory = new ConfiteriaFactory();

        // Se pasa la función seleccionada y el AsientoFuncion encontrado
        Entrada entrada = entradaFactory.crearEntrada(funcionSeleccionada, asientoFuncion);

        // Se pasan los 4 parámetros requeridos por ConfiteriaFactory (ID, Nombre, Precio, Descripción)
        ProductoConfiteria producto = confiteriaFactory.crearProducto(
                "CONF-01",
                "Crispetas Grandes",
                12000.0,
                "Crispetas tradicionales de sal"
        );

        try {
            // Construcción con CompraBuilder
            Compra nuevaCompra = new Compra.CompraBuilder()
                    .setIdCompra("COMP-" + System.currentTimeMillis())
                    .setCliente(cliente)
                    .agregarItem(entrada)
                    .agregarItem(producto)
                    .build();

            engine.procesarCompra(nuevaCompra);

            System.out.println("¡Compra realizada con éxito!");
            System.out.println("ID Compra: " + nuevaCompra.getIdCompra());
            System.out.println("Total Pagado: $" + nuevaCompra.getTotal());

        } catch (SaldoInsuficienteException e) {
            System.out.println("Error en la compra: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al procesar la compra: " + e.getMessage());
        }
    }

    private static void mostrarEstadoCuenta(Cliente cliente) {
        System.out.println("\n--- ESTADO DE CUENTA ---");
        System.out.println("Saldo Disponible: $" + cliente.getTarjetaVirtual().getSaldo());
        System.out.println("Puntos Validos: " + cliente.getPuntosValidosTotales());
    }

    private static void procesarReembolsoCliente() {
        System.out.print("Ingrese el ID de la compra a cancelar: ");
        String idCompra = scanner.nextLine();

        if (engine.cancelarYReembolsarCompra(idCompra)) {
            System.out.println("Reembolso procesado exitosamente. El saldo fue devuelto a su tarjeta.");
        } else {
            System.out.println("No se pudo realizar el reembolso (Compra no encontrada o ya cancelada).");
        }
    }

    private static void menuAdministrador(Administrador admin) {
        int opcion;
        do {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Recargar Tarjeta de Cliente");
            System.out.println("2. Ver Historial General de Compras");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el número de la tarjeta: ");
                    String numTarjeta = scanner.nextLine();
                    System.out.print("Monto a recargar: ");
                    double monto = scanner.nextDouble();
                    scanner.nextLine();

                    engine.getClientes().stream()
                            .filter(c -> c.getTarjetaVirtual().getNumeroTarjeta().equalsIgnoreCase(numTarjeta))
                            .findFirst()
                            .ifPresentOrElse(
                                    c -> {
                                        admin.recargarTarjeta(c.getTarjetaVirtual(), monto);
                                        System.out.println("Recarga realizada. Nuevo saldo: $" + c.getTarjetaVirtual().getSaldo());
                                    },
                                    () -> System.out.println("Tarjeta no encontrada.")
                            );
                }
                case 2 -> {
                    System.out.println("\n--- COMPRAS REGISTRADAS ---");
                    for (Compra c : engine.getCompras()) {
                        System.out.println("ID: " + c.getIdCompra() + " | Cliente: " + c.getCliente().getNombre() + " | Total: $" + c.getTotal() + " | Estado: " + c.getEstado());
                    }
                }
                case 0 -> engine.cerrarSesion();
            }
        } while (opcion != 0 && engine.getUsuarioAutenticado() != null);
    }
}