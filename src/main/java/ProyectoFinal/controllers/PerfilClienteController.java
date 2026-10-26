package ProyectoFinal.controllers;

import ProyectoFinal.model.Cliente;
import ProyectoFinal.model.Compra;
import ProyectoFinal.services.CineFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class PerfilClienteController {

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblSaldo;
    @FXML private Label lblPuntos;
    @FXML private ListView<String> listHistorial;

    private final CineFacade cineFacade = CineFacade.getInstancia();
    private Cliente clienteActual;

    @FXML
    public void initialize() {
        if (cineFacade.getUsuarioAutenticado() instanceof Cliente) {
            this.clienteActual = (Cliente) cineFacade.getUsuarioAutenticado();
            actualizarDatosPantalla();
        }
    }

    private void actualizarDatosPantalla() {
        if (clienteActual == null) return;

        lblNombre.setText("Cliente: " + clienteActual.getNombre());
        lblCorreo.setText("Correo: " + clienteActual.getCorreo());

        if (clienteActual.getTarjetaVirtual() != null) {
            lblSaldo.setText("Saldo Tarjeta Virtual: $" + clienteActual.getTarjetaVirtual().getSaldo());
        }

        if (clienteActual.getRegistroPuntos() != null) {
            lblPuntos.setText("Puntos Fidelidad Acumulados: " + clienteActual.getRegistroPuntos().getPuntosTotales() + " pts");
        }

        listHistorial.getItems().clear();
        if (clienteActual.getHistorialCompras() != null) {
            for (Compra c : clienteActual.getHistorialCompras()) {
                listHistorial.getItems().add("Compra #" + c.getId() + " - Total: $" + c.getTotal() + " | Fecha: " + c.getFechaHora());
            }
        }
    }

    @FXML
    public void handleRecargarTarjeta(ActionEvent event) {
        if (clienteActual != null && clienteActual.getTarjetaVirtual() != null) {
            clienteActual.getTarjetaVirtual().recargar(50000.0);
            actualizarDatosPantalla();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recarga Exitosa");
            alert.setHeaderText(null);
            alert.setContentText("Se han abonado $50.000 exitosamente a su Tarjeta Virtual.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleVolverCartelera(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/CarteleraView.fxml"));
            Stage stage = (Stage) lblNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CinemaUQ - Cartelera Principal");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCerrarSesion(ActionEvent event) {
        cineFacade.cerrarSesion();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
            Stage stage = (Stage) lblNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CinemaUQ - Inicio de Sesión");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}