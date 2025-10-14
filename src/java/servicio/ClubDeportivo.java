package java.servicio;

import java.exceptions.*;
import java.modelo.Pista;
import java.modelo.Reserva;
import java.modelo.Socio;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClubDeportivo {

    private ArrayList<Socio> socios;
    private ArrayList<Pista> pistas;
    private ArrayList<Reserva> reservas;
    public final String FICHERO_DATOS_SOCIOS="";
    public final String FICHERO_DATOS_PISTA="";
    public final String FICHERO_DATOS_RESERVA="";

    public ClubDeportivo() {
        this.socios = new ArrayList<>();
        this.pistas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    /**
     * Metodo para dar de alta a un socio, se validará si existe un socio con ese ID.
     * @param socio
     * @return
     */

    public boolean altaSocio(Socio socio){
        for (Socio s : socios){
            if(s.getIdSocio().equals(socio.getIdSocio())){
                // Si existe socio con ese id, lanzaremos la exception creada.
                throw new ClienteExistenteException("El cliente con el id " + socio.getIdSocio() + " ya existe.");
            }
        }
        // Si no existe un usuario con ese ID, procedemos a darle de alta
        socios.add(socio);
        return true;
    }

    /**
     * Metodo para dar de baja un socio.
     * @param idSocio
     * @throws ReservaPendienteException
     *
     */
    public void bajaSocio(String idSocio){
        for (Reserva r : reservas){
            if (r.getIdSocio().equals(idSocio)){
                throw new ReservaPendienteException(
                        "El socio con ID " + idSocio + " no se puede dar de baja porque tiene reservas pendientes"
                );
            }
        }
        boolean socioElminado = socios.removeIf(s -> s.getIdSocio().equals(idSocio));
        if (!socioElminado){
            throw new SocioNoExisteException("No existe un socio con el ID " +idSocio + " así que no hay ningún socio por eliminar.");
        }
    }
    public void altaPista(Pista pista){
        for (Pista p : pistas){
           if (p.getIdPista().equals(pista.getIdPista())){
               throw new PistaExistenteException("La pista con ID " + pista.getIdPista() + " ya existe");
           }
       }
            pistas.add(pista);
    }

    public void cambiarDisponibilidadPista(String idPista, boolean disponible){
        for (Pista p : pistas){
            if (p.getIdPista().equals(idPista)){
                p.setDisponible(disponible);
                return;
            }
        }
        // Si llega a esta parte del código significa que la pista con ese ID no existe
        // ya que si existiera, salimos pro el return del for.
        throw new PistaNoExisteException("La pista con el ID " + idPista + " no existe");
    }

    public boolean crearReserva(Reserva r) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdPista().equals(r.getIdPista()) && reserva.getFecha().equals(r.getFecha())) {
                LocalTime inicioReservaNueva = r.getHoraInicio();
                LocalTime finReservaNueva = r.getHoraInicio().plusHours(r.getDuracionMinima());
                LocalTime inicioReservaExistente = reserva.getHoraInicio();
                LocalTime finReservaExistente = reserva.getHoraInicio().plusHours(reserva.getDuracionMinima());
                // Aqui hacemos un boolean para ver si las pista a reservar estara ocupada
                boolean ocupadas = !(finReservaNueva.isBefore(inicioReservaExistente) || finReservaExistente.isBefore(inicioReservaNueva));
                if (ocupadas) {
                    return false;
                }
            }
        }
        reservas.add(r);
        return true;
    }

   public void cancelarReserva(String idReserva){
        if (!reservas.removeIf(s -> s.getIdReserva().equals(idReserva))){
            throw new ReservaNoExisteException("La reserva con el id" +idReserva + "que quiere cancelar no existe.");
        }
    }

    public ArrayList<Socio> getSocios() {return socios; }

    public ArrayList<Pista> getPistas() {
        return pistas;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    }

