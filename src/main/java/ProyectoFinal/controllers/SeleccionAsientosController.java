package ProyectoFinal.controllers;

import ProyectoFinal.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeleccionAsientosController {

    @FXML private Label lblPeliculaInfo;
    @FXML private GridPane gridAsientos;

    private Funcion funcionActual;
    private final List<AsientoFuncion> asientosSeleccionados = new ArrayList<>();

    public void setFuncionActual(Funcion funcion) {
        this.funcionActual = funcion;
        if (lblPeliculaInfo != null && funcion != null && funcion.getPelicula() != null) {
            lblPeliculaInfo.setText("Película: " + funcion.getPelicula().getTitulo() + " | Precio: $" + funcion.getPrecioEntrada());
        }
        dibujarMapaAsientos();
    }

    private void dibujarMapaAsientos() {
        if (funcionActual == null || funcionActual.getSala() == null) return;

        gridAsientos.getChildren().clear();
        asientosSeleccionados.clear();

        Sala sala = funcionActual.getSala();
        int filas = sala.getFilas();
        int columnas = sala.getColumnas();

        for (int f = 1; f <= filas; f++) {
            for (int c = 1; c <= columnas; c++) {
                Asiento asientoModel = new Asiento(f, c, false);
                AsientoFuncion af = new AsientoFuncion(asientoModel);

                Button btnAsiento = new Button("F" + f + "C" + c);
                btnAsiento.setPrefSize(60, 40);
                btnAsiento.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

                btnAsiento.setOnAction(e -> toggleSeleccionAsiento(btnAsiento, af));

                gridAsientos.add(btnAsiento, c - 1, f - 1);
            }
        }
    }

    private void toggleSeleccionAsiento(Button btn, AsientoFuncion af) {
        if (asientosSeleccionados.contains(af)) {
            asientosSeleccionados.remove(af);
            btn.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        } else {
            if (asientosSeleccionados.size() >= 8) {
                mostrarAlerta(Alert.AlertType.WARNING, "Límite Alcanzado", "Regla RN-010: No puede adquirir más de 8 asientos por compra.");
                return;
            }
            asientosSeleccionados.add(af);
            btn.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

    @FXML
    public void handleConfirmarCompra(ActionEvent event) {
        if (asientosSeleccionados.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Requerida", "Debe seleccionar al menos un asiento en la grilla para continuar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ConfiteriaView.fxml"));
            Parent root = loader.load();

            ConfiteriaController controller = loader.getController();
            controller.setDatosCompra(funcionActual, asientosSeleccionados);

            Stage stage = (Stage) gridAsientos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CinemaUQ - Confitería");
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Navegación", "No se pudo cargar la pantalla de confitería: " + e.getMessage());
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