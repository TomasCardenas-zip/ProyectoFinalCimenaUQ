package ProyectoFinal.controllers;

import ProyectoFinal.exception.CinemaUQException;
import ProyectoFinal.model.*;
import ProyectoFinal.services.CineFacade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfiteriaController {

    @FXML private ListView<ProductoConfiteria> listProductos;
    @FXML private Label lblResumenAsientos;
    @FXML private Label lblTotalPagar;

    private Funcion funcionActual;
    private List<AsientoFuncion> asientosSeleccionados;
    private final CineFacade cineFacade = CineFacade.getInstancia();

    public void setDatosCompra(Funcion funcion, List<AsientoFuncion> asientos) {
        this.funcionActual = funcion;
        this.asientosSeleccionados = asientos;

        double subtotalAsientos = asientos.size() * funcion.getPrecioEntrada();
        lblResumenAsientos.setText("Asientos seleccionados: " + asientos.size() + " ($" + subtotalAsientos + ")");
        lblTotalPagar.setText("Total a Pagar: $" + subtotalAsientos);

        cargarProductos();
    }

    private void cargarProductos() {
        listProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listProductos.setItems(FXCollections.observableArrayList(cineFacade.getProductosConfiteria()));

        listProductos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ProductoConfiteria item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " - $" + item.getPrecio());
                }
            }
        });

        listProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> recalcularTotal());
    }

    private void recalcularTotal() {
        double totalAsientos = asientosSeleccionados.size() * funcionActual.getPrecioEntrada();
        double totalSnacks = listProductos.getSelectionModel().getSelectedItems()
                .stream().mapToDouble(ProductoConfiteria::getPrecio).sum();

        lblTotalPagar.setText("Total a Pagar: $" + (totalAsientos + totalSnacks));
    }

    @FXML
    public void handleFinalizarCompra(ActionEvent event) {
        try {
            Cliente cliente = (Cliente) cineFacade.getUsuarioAutenticado();
            List<ProductoConfiteria> snacks = new ArrayList<>(listProductos.getSelectionModel().getSelectedItems());

            Compra compra = cineFacade.realizarCompraGlobal(cliente, funcionActual, asientosSeleccionados, snacks);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Compra Exitosa!");
            alert.setHeaderText("Transacción Procesada Correctamente");
            alert.setContentText("Total cobrado de Tarjeta Virtual: $" + compra.getTotal() +
                    "\nPuntos acumulados: " + (int)(compra.getTotal() / 1000 * 10));
            alert.showAndWait();

            abrirPerfilCliente();

        } catch (CinemaUQException e) {
            mostrarAlerta("Error en la Transacción", e.getMessage());
        }
    }

    private void abrirPerfilCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PerfilClienteView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) listProductos.getScene().getWindow();
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