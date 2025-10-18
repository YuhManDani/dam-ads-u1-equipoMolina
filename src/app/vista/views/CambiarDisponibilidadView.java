package app.vista.views;

import app.modelo.Pista;
import app.servicio.ClubDeportivo;
import app.modelo.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CambiarDisponibilidadView extends GridPane {
    public CambiarDisponibilidadView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Pista> id = new ComboBox();
        CheckBox disponible = new CheckBox("Disponible");
        Button cambiar = new Button("Aplicar");
        for (Pista p : club.getPistas()){
            id.getItems().add(p);
        }
        addRow(0, new Label("idPista"), id);
        addRow(1, new Label("Estado"), disponible);
        add(cambiar, 1, 2);

        cambiar.setOnAction(e -> {
            try {
                //club.cambiarDisponibilidadPista(id.getText(), disponible.isSelected());
                Pista pistaSeleccionada = id.getValue();
                String pistaId = pistaSeleccionada.getIdPista();
                club.cambiarDisponibilidadPista(pistaId, disponible.isSelected());
                if (disponible.isSelected()){
                    showInfo("Se ha cambiado la disponibilidad de la pista, ahora esta disponible");
                }
                else{
                    showInfo("Se ha cambiado la disponibilidad de la pista, ahora no esta disponible");
                }

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
