package ProyectoFinal.controllers;

import ProyectoFinal.model.Funcion;
import ProyectoFinal.services.CineFacade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CarteleraController {

    @FXML private ListView<Funcion> listFunciones;

    private final CineFacade cineFacade = CineFacade.getInstancia();

    @FXML
    public void initialize() {
        listFunciones.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Funcion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String titulo = (item.getPelicula() != null) ? item.getPelicula().getTitulo() : "Sin título";
                    String genero = (item.getPelicula() != null) ? item.getPelicula().getGenero() : "General";
                    setText(titulo + " [" + genero + "] - $" + item.getPrecioEntrada());
                }
            }
        });

        listFunciones.setItems(FXCollections.observableArrayList(cineFacade.getFuncionesDisponibles()));
    }

    @FXML
    public void handleContinuar(ActionEvent event) {
        Funcion funcionSeleccionada = listFunciones.getSelectionModel().getSelectedItem();

        if (funcionSeleccionada == null) {
            mostrarAlerta("Selección Requerida", "Por favor seleccione una función de la lista antes de continuar.");
            return;
        }

        try {
            URL resource = getClass().getResource("/views/SeleccionAsientosView.fxml");
            if (resource == null) {
                mostrarAlerta("Error de Vista", "No se encontró el archivo SeleccionAsientosView.fxml en src/main/resources/views/");
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            SeleccionAsientosController controller = loader.getController();
            controller.setFuncionActual(funcionSeleccionada);

            Stage stage = (Stage) listFunciones.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CinemaUQ - Selección de Asientos");
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace(); // Imprime la causa exacta en la consola
            mostrarAlerta("Error al abrir vista", "Ocurrió un error al cargar los asientos: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}