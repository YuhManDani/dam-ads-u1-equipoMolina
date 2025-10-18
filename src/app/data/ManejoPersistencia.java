package app.data;

import app.modelo.Pista;
import app.modelo.Reserva;
import app.modelo.Socio;
import app.servicio.ClubDeportivo;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

public class ManejoPersistencia {

    public static void guardarEnXML(ClubDeportivo club) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document documento = builder.newDocument();

        Element raiz = documento.createElement("club");

        Element nodoSocios = documento.createElement("socios");
        Element nodoReservas = documento.createElement("reservas");
        Element nodoPistas = documento.createElement("pistas");
        Text texto = null;

        raiz.appendChild(nodoSocios);
        raiz.appendChild(nodoReservas);
        raiz.appendChild(nodoPistas);

        // Creacion de los nodos <socio/>
        for (Socio s : club.getSocios()) {
            // Creamos un nuevo nodo socio
            Element nodoSocio = documento.createElement("socio");

            Element nodoIdSocio = documento.createElement("idSocio");
            texto = documento.createTextNode(s.getIdSocio());
            nodoIdSocio.appendChild(texto);

            Element nodoDni = documento.createElement("dni");
            texto = documento.createTextNode(s.getDni());
            nodoDni.appendChild(texto);

            Element nodoNombre = documento.createElement("nombre");
            texto = documento.createTextNode(s.getNombre());
            nodoNombre.appendChild(texto);

            Element nodoApellidos = documento.createElement("apellidos");
            texto = documento.createTextNode(s.getApellidos());
            nodoApellidos.appendChild(texto);

            Element nodoTelefono = documento.createElement("telefono");
            texto = documento.createTextNode(s.getTelefono());
            nodoTelefono.appendChild(texto);

            Element nodoEmail = documento.createElement("email");
            texto = documento.createTextNode(s.getEmail());
            nodoEmail.appendChild(texto);

            nodoSocio.appendChild(nodoIdSocio);
            nodoSocio.appendChild(nodoDni);
            nodoSocio.appendChild(nodoNombre);
            nodoSocio.appendChild(nodoApellidos);
            nodoSocio.appendChild(nodoTelefono);
            nodoSocio.appendChild(nodoEmail);

            nodoSocios.appendChild(nodoSocio);
        }

        //Creacion de los nodos <pista/>

        for (Pista p : club.getPistas()) {
            // Creamos un nuevo nodo pista
            Element nodoPista = documento.createElement("pista");

            Element nodoIdPista = documento.createElement("idPista");
            texto = documento.createTextNode(p.getIdPista());
            nodoIdPista.appendChild(texto);

            Element nodoDeporte = documento.createElement("deporte");
            texto = documento.createTextNode(p.getDeporte());
            nodoDeporte.appendChild(texto);

            Element nodoDescripcion = documento.createElement("descripcion");
            texto = documento.createTextNode(p.getDescripcion());
            nodoDescripcion.appendChild(texto);

            Element nodoDisponible = documento.createElement("disponible");
            if (p.getDisponible()) {
                texto = documento.createTextNode("true");
            } else {
                texto = documento.createTextNode("false");
            }
            nodoDisponible.appendChild(texto);
            nodoPista.appendChild(nodoIdPista);
            nodoPista.appendChild(nodoDeporte);
            nodoPista.appendChild(nodoDescripcion);
            nodoPista.appendChild(nodoDisponible);

            nodoPistas.appendChild(nodoPista);

        }
        //Creacion de los nodos <reserva/>
        for (Reserva r : club.getReservas()) {
            // Creamos un nuevo nodo reserva
            Element nodoReserva = documento.createElement("reserva");

            Element nodoIdReserva = documento.createElement("idReserva");
            texto = documento.createTextNode(r.getIdReserva());
            nodoIdReserva.appendChild(texto);

            Element nodoIdSocio = documento.createElement("idSocio");
            texto = documento.createTextNode(r.getIdSocio());
            nodoIdSocio.appendChild(texto);

            Element nodoIdPista = documento.createElement("idPista");
            texto = documento.createTextNode(r.getIdPista());
            nodoIdPista.appendChild(texto);

            Element nodoFecha = documento.createElement("fecha");
            texto = documento.createTextNode(r.getFecha().toString());
            nodoFecha.appendChild(texto);

            Element nodoHoraInicio = documento.createElement("horaInicio");
            texto = documento.createTextNode(r.getHoraInicio().toString());
            nodoHoraInicio.appendChild(texto);

            Element nodoDuracionMinima = documento.createElement("duracionMinima");
            texto = documento.createTextNode(Integer.toString(r.getDuracionMin()));
            nodoDuracionMinima.appendChild(texto);

            Element nodoPrecio = documento.createElement("precio");
            texto = documento.createTextNode(Double.toString(r.getPrecio()));
            nodoPrecio.appendChild(texto);

            nodoReserva.appendChild(nodoIdReserva);
            nodoReserva.appendChild(nodoIdSocio);
            nodoReserva.appendChild(nodoIdPista);
            nodoReserva.appendChild(nodoFecha);
            nodoReserva.appendChild(nodoHoraInicio);
            nodoReserva.appendChild(nodoDuracionMinima);
            nodoReserva.appendChild(nodoPrecio);

            nodoReservas.appendChild(nodoReserva);

        }
        documento.appendChild(raiz);
        Source src = new DOMSource(documento);
        Result resultado = new StreamResult(new File("src\\app\\data\\datos.xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(src, resultado);
    }

    public static ClubDeportivo cargarClubDesdeXML() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document documento = builder.parse(new File("src\\app\\data\\datos.xml"));

        documento.getDocumentElement().normalize();

        NodeList listaSocios = documento.getElementsByTagName("socio");
        NodeList listaPistas = documento.getElementsByTagName("pista");
        NodeList listaReservas = documento.getElementsByTagName("reserva");

        ClubDeportivo club = new ClubDeportivo();

        recorrerSocios(listaSocios, club);
        recorrerPistas(listaPistas, club);
        recorrerReservas(listaReservas, club);

        return club;
    }

    private static void recorrerSocios(NodeList listaSocios, ClubDeportivo club) {
        for (int i = 0; i < listaSocios.getLength(); i++) {
            Node nodoSocio = listaSocios.item(i);

            if (nodoSocio.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoSocio = (Element) nodoSocio;

                String idSocio = elementoSocio.getElementsByTagName("idSocio").item(0).getChildNodes().item(0).getNodeValue();
                String dni = elementoSocio.getElementsByTagName("dni").item(0).getChildNodes().item(0).getNodeValue();
                String nombre = elementoSocio.getElementsByTagName("nombre").item(0).getChildNodes().item(0).getNodeValue();
                String apellidos = elementoSocio.getElementsByTagName("apellidos").item(0).getChildNodes().item(0).getNodeValue();
                String telefono = elementoSocio.getElementsByTagName("telefono").item(0).getChildNodes().item(0).getNodeValue();
                String email = elementoSocio.getElementsByTagName("email").item(0).getChildNodes().item(0).getNodeValue();


                Socio nuevoSocio = new Socio(idSocio, dni, nombre, apellidos, telefono, email);
                club.altaSocio(nuevoSocio);
            }
        }
    }

    private static void recorrerPistas(NodeList listaPistas, ClubDeportivo club) {
        for (int i = 0; i < listaPistas.getLength(); i++) {
            Node nodoPista = listaPistas.item(i);

            if (nodoPista.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoPista = (Element) nodoPista;

                String idPista = elementoPista.getElementsByTagName("idPista").item(0).getChildNodes().item(0).getNodeValue();
                String deporte = elementoPista.getElementsByTagName("deporte").item(0).getChildNodes().item(0).getNodeValue();
                String descripcion = elementoPista.getElementsByTagName("descripcion").item(0).getChildNodes().item(0).getNodeValue();

                // Conversión de String a boolean usando el método más largo
                boolean disponible = Boolean.parseBoolean(
                        elementoPista.getElementsByTagName("disponible").item(0).getChildNodes().item(0).getNodeValue()
                );

                Pista nuevaPista = new Pista(idPista, disponible, descripcion, deporte);
                club.altaPista(nuevaPista);
            }
        }
    }

    private static void recorrerReservas(NodeList listaReservas, ClubDeportivo club) {
        for (int i = 0; i < listaReservas.getLength(); i++) {
            Node nodoReserva = listaReservas.item(i);

            if (nodoReserva.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoReserva = (Element) nodoReserva;

                String idReserva = elementoReserva.getElementsByTagName("idReserva").item(0).getChildNodes().item(0).getNodeValue();
                String idSocio = elementoReserva.getElementsByTagName("idSocio").item(0).getChildNodes().item(0).getNodeValue();
                String idPista = elementoReserva.getElementsByTagName("idPista").item(0).getChildNodes().item(0).getNodeValue();

                LocalDate fecha = LocalDate.parse(elementoReserva.getElementsByTagName("fecha").item(0).getChildNodes().item(0).getNodeValue());
                LocalTime horaInicio = LocalTime.parse(elementoReserva.getElementsByTagName("horaInicio").item(0).getChildNodes().item(0).getNodeValue());

                int duracionMinima = Integer.parseInt(elementoReserva.getElementsByTagName("duracionMinima").item(0).getChildNodes().item(0).getNodeValue());
                double precio = Double.parseDouble(elementoReserva.getElementsByTagName("precio").item(0).getChildNodes().item(0).getNodeValue());

                Reserva nuevaReserva = new Reserva(idReserva, idSocio, idPista, fecha, horaInicio, duracionMinima, precio);
                club.crearReserva(nuevaReserva);
            }
        }
    }
}