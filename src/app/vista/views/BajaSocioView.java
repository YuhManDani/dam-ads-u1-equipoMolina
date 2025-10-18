package app.vista.views;

import app.exceptions.ReservaPendienteException;
import app.modelo.Socio;
import app.servicio.ClubDeportivo;
import app.modelo.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class BajaSocioView extends GridPane {
    public BajaSocioView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Socio> id = new ComboBox<>();
        Button baja = new Button("Dar de baja");
        for (Socio s : club.getSocios()){
            id.getItems().add(s);
        }

        addRow(0, new Label("Socio"), id);
        add(baja, 1, 1);

        baja.setOnAction(e -> {
        //LLamar al método del modelo para dar de baja  a un socio.
            Socio socioSeleccionado = id.getValue();
            if(socioSeleccionado == null){
                showError("No has seleccionado un socio.");
            }
            String socioId = socioSeleccionado.getIdSocio();
            try {
                club.bajaSocio(socioId);
                showInfo("El socio " +socioSeleccionado.getNombre() + " ha sido dado de baja");
            }catch (ReservaPendienteException ex){
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
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
