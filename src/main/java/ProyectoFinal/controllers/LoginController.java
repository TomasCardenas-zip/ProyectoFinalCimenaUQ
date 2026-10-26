package ProyectoFinal.controllers;

import ProyectoFinal.services.CineFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final CineFacade cineFacade = CineFacade.getInstancia();

    @FXML
    public void handleLogin(ActionEvent event) {
        String correo = txtCorreo.getText() != null ? txtCorreo.getText().trim() : "";

        if (correo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Vacío", "Por favor ingrese su correo electrónico.");
            return;
        }

        boolean autenticado = cineFacade.autenticar(correo);

        if (autenticado) {
            String nombre = cineFacade.getUsuarioAutenticado().getNombre();

            // 1. Muestra la alerta
            mostrarAlerta(Alert.AlertType.INFORMATION, "Bienvenido", "Inicio de sesión exitoso como: " + nombre);

            // 2. Transición a la Cartelera al presionar Aceptar
            cargarVistaCartelera();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Autenticación", "Usuario no encontrado en el sistema.");
        }
    }

    private void cargarVistaCartelera() {
        try {
            URL location = getClass().getResource("/views/CarteleraView.fxml");
            if (location == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Archivo", "No se encontró CarteleraView.fxml en src/main/resources/views/");
                return;
            }

            Parent root = FXMLLoader.load(location);
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CinemaUQ - Cartelera Principal");
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Carga", "Ocurrió un problema al abrir la cartelera: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}