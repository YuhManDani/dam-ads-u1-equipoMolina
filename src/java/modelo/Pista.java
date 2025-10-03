package java.modelo;

public class Pista {

    private String idPista;
    private String deporte;
    private String descripcion;
    private boolean disponible;

    public Pista(String idPista, boolean disponible, String descripcion, String deporte) {
        this.idPista = idPista;
        this.disponible = disponible;
        this.descripcion = descripcion;
        this.deporte = deporte;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
