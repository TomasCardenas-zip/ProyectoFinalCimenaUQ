package ProyectoFinal.controllers;

import ProyectoFinal.exception.CinemaUQException;
import ProyectoFinal.model.AsientoFuncion;
import ProyectoFinal.model.Cliente;
import ProyectoFinal.model.Compra;
import ProyectoFinal.model.Funcion;
import ProyectoFinal.model.ProductoConfiteria;
import ProyectoFinal.services.CineFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CompraController {

    @FXML private Label lblPelicula;
    @FXML private Label lblAsientos;
    @FXML private Label lblTotal;

    private Funcion funcionActual;
    private List<AsientoFuncion> asientosSeleccionados;
    private List<ProductoConfiteria> snacksSeleccionados;

    private final CineFacade cineFacade = CineFacade.getInstancia();

    public void setDatosCompra(Funcion funcion, List<AsientoFuncion> asientos, List<ProductoConfiteria> snacks) {
        this.funcionActual = funcion;
        this.asientosSeleccionados = asientos;
        this.snacksSeleccionados = snacks;

        double totalEntradas = (asientos != null && funcion != null) ? asientos.size() * funcion.getPrecioEntrada() : 0.0;
        double totalSnacks = (snacks != null) ? snacks.stream().mapToDouble(ProductoConfiteria::getPrecio).sum() : 0.0;
        double totalGeneral = totalEntradas + totalSnacks;

        if (lblPelicula != null && funcion != null && funcion.getPelicula() != null) {
            lblPelicula.setText("Película: " + funcion.getPelicula().getTitulo());
        }
        if (lblAsientos != null && asientos != null) {
            lblAsientos.setText("Cantidad de Asientos: " + asientos.size());
        }
        if (lblTotal != null) {
            lblTotal.setText("Total a Pagar: $" + totalGeneral);
        }
    }

    @FXML
    public void handleProcesarPago(ActionEvent event) {
        try {
            Cliente cliente = (Cliente) cineFacade.getUsuarioAutenticado();

            Compra compra = cineFacade.realizarCompraGlobal(cliente, funcionActual, asientosSeleccionados, snacksSeleccionados);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Compra Exitosa!");
            alert.setHeaderText("Transacción Procesada Exitosamente");
            alert.setContentText("Código de Compra: " + compra.getId() + "\nTotal Debitado: $" + compra.getTotal());
            alert.showAndWait();

            abrirPerfilCliente();

        } catch (CinemaUQException e) {
            mostrarAlerta("Error en la Compra", e.getMessage());
        }
    }

    private void abrirPerfilCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PerfilClienteView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) lblTotal.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CinemaUQ - Mi Perfil y Tarjeta Virtual");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}