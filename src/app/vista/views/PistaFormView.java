package app.vista.views;

import app.modelo.*;

import app.modelo.Pista;
import app.servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class PistaFormView extends GridPane {
    public PistaFormView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        TextField id = new TextField();
        TextField deporte = new TextField();
        TextField descripcion = new TextField();
        CheckBox disponible = new CheckBox("Disponible");
        Button crear = new Button("Crear");

        addRow(0, new Label("idPista*"), id);
        addRow(1, new Label("Deporte"), deporte);
        addRow(2, new Label("Descripción"), descripcion);
        addRow(3, new Label("Operativa"), disponible);
        add(crear, 1, 4);

        crear.setOnAction(e -> {
            try {
             //   club.altaPista(new Pista(id.getText(), deporte.getText(), descripcion.getText(), disponible.isSelected()));
                String idPista = id.getText();
                String deporteElegido = deporte.getText();
                String descripcionPista = descripcion.getText();
                Boolean operativa = disponible.isSelected();
                club.altaPista(new Pista(idPista, operativa, descripcionPista, deporteElegido));
                showInfo("Se ha dado de alta la pista con ID " +id.getText());
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
