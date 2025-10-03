package java.modelo;

import java.util.ArrayList;
import java.exceptions.ReservaPendienteException;

public class ClubDeportivo {

    ArrayList<Socio> socios = new ArrayList<>();;
    ArrayList<Pista> pistas = new ArrayList<>();;
    ArrayList<Reserva> reservas = new ArrayList<>();;

    public ClubDeportivo(ArrayList<Socio> socios, ArrayList<Pista> pistas, ArrayList<Reserva> reservas) {
        this.socios = socios;
        this.pistas = pistas;
        this.reservas = reservas;
    }

    /**
     * Metodo para dar de alta a un socio, se validará si existe un socio con ese ID.
     * @param socio
     * @return
     */

    boolean altaSocio(Socio socio){
        // De primeras, inicializaremos la validación a true;
        boolean checkSocios = true;
        for (Socio s : socios){
            if(s.getIdSocio().equals(socio.getIdSocio())){
                // Si existe socio con ese id, le cambiamos el valor a false
                checkSocios = false;
            }
        }
        // Si no existe un usuario con ese ID, procedemos a darle de alta
        if(checkSocios){
            socios.add(socio);
        }
        return checkSocios;
    }

    /**
     * Metodo para dar de baja un socio.
     * @param idSocio
     * @throws ReservaPendienteException
     *
     */
    void bajaSocio(String idSocio) throws ReservaPendienteException{
        for (Reserva r : reservas){
            if (r.getIdSocio().equals(idSocio)){
                throw new ReservaPendienteException("El socio con ID" + idSocio + "no se puede dar de baja por que tiene reservas pendientes")
            }
        }
        socios.removeIf(s -> s.getIdSocio().equals(idSocio));
    }
}
