package java.modelo;

import java.exceptions.DuracionMinimaException;
import java.exceptions.PrecioMinimoException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private String idReserva;
    private String idSocio;
    private String idPista;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private int duracionMinima;
    private double precio;

    /**
     * Constructor de un objeto reserva.
     * @param idReserva
     * @param idSocio
     * @param idPista
     * @param fecha
     * @param horaInicio
     * @param duracionMinima
     * @param precio
     * En el constructor le pasamos todos los datos que desde la interfaz corregiremos
     * validaremos la duración y el precio con los Setters, los cuales tienen en cuenta
     * un posible fallo de dato
     */
    public Reserva(String idReserva, String idSocio, String idPista, LocalDate fecha, LocalTime horaInicio, int duracionMinima, Double precio) {
        this.idReserva = idReserva;
        this.idSocio = idSocio;
        this.idPista = idPista;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        setDuracionMinima(duracionMinima);
        setPrecio(precio);
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio <= 0){
            throw new PrecioMinimoException("El precio no puede ser 0 o menor");
        }
        else {
            this.precio = precio;
        }
    }

    public int getDuracionMinima() {
        return duracionMinima;
    }

    public void setDuracionMinima(int duracionMinima) {
        if (duracionMinima < 1 ){
            throw new DuracionMinimaException("La duración tiene que ser del al menos 1 hora");
        }
        this.duracionMinima = duracionMinima;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }
}
