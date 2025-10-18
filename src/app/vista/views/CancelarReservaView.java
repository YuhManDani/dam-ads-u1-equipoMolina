package app.vista.views;
import app.modelo.*;

import app.modelo.Reserva;
import app.servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CancelarReservaView extends GridPane {
    public CancelarReservaView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Reserva> id = new ComboBox();
        for (Reserva r : club.getReservas()){
            id.getItems().add(r);
        }
        Button cancelar = new Button("Cancelar reserva");

        addRow(0, new Label("Reserva"), id);
        add(cancelar, 1, 1);

        cancelar.setOnAction(e -> {
            try {
                //club.cancelarReserva(id.getValue());
                Reserva reservaSeleccionada = id.getValue();
                String reservaId = reservaSeleccionada.getIdReserva();
                club.cancelarReserva(reservaId);
                showInfo("Se ha cancelado la reserva con ID " +id.getValue());
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
