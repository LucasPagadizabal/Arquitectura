package servicios;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;



import entidades.Reunion;

@Path("/reuniones")
public class ReunionREST {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reunion> getAllReunion(){
		return ReunionDAO.getInstance().findAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Reunion getUserById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Reunion reunion = ReunionDAO.getInstance().findById(id);
		if(reunion!=null)
			return reunion;
		else
			throw new RecursoNoEncontrado();
	}

	@GET
	@Path("/getReunionesByUserAndDay")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reunion> getReunionesByUserAndDay(@QueryParam("iduser") int iduser, @QueryParam("day") String day) {
		int dni = Integer.valueOf(iduser);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date dateDay = sdf.parse(day);
			List<Reunion> reuniones = ReunionDAO.getInstance().getReunionesByUserAndDay(dni, dateDay);

			if(reuniones!=null)	return reuniones;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		throw new RecursoNoEncontrado();
	}


	@GET
	@Path("/getReunionesEntreFechas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reunion> getReunionesEntreFechas(@QueryParam("day1") String day1, @QueryParam("day2") String day2) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateDay1=null;
		Date dateDay2=null;
		try {
			dateDay1 = sdf.parse(day1);
			dateDay2 = sdf.parse(day2);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(dateDay1!=null && dateDay2!=null) {
			List<Reunion> reuniones = ReunionDAO.getInstance().getReunionesEntreFechas(dateDay1, dateDay2);
			if(reuniones!=null)	return reuniones;

		}
		throw new RecursoNoEncontrado();

	}

	@GET
	@Path("/getReunionesSuperpuestas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reunion> getReunionesSuperpuestas(@QueryParam("iduser") int iduser, @QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date reunionIni=null;
		Date reunionFin=null;
		try {
			reunionIni = sdf.parse(fechaInicio);
			reunionFin = sdf.parse(fechaFin);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(reunionIni!=null && reunionFin!=null) {
			List<Reunion> reuniones = ReunionDAO.getInstance().getReunionesSuperpuestas(iduser, reunionIni, reunionFin);
			if(reuniones!=null)	return reuniones;

		}
		throw new RecursoNoEncontrado();

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createReunion(ObjectNode json) throws ParseException{
		
		Date fechaInicio = new GregorianCalendar(json.get("anio").intValue(),json.get("mes").intValue(),json.get("dia").intValue(),json.get("horaI").intValue(),00,00).getTime();
		Date fechaFin = new GregorianCalendar(json.get("anio").intValue(),json.get("mes").intValue(),json.get("dia").intValue(),json.get("horaF").intValue(),00,00).getTime();

		int idSala = json.get("idSala").intValue();
		int idCalendario = json.get("idCalendario").intValue();
		
		Reunion reunion= ReunionDAO.getInstance().createReunion(fechaInicio, fechaFin, idSala, idCalendario);
		if(reunion!=null)	return Response.status(201).entity(reunion).build();

		////		throw new RecursoDuplicado(reunion.getId());
		throw new RecursoNoCreado();
	}
	
	public class RecursoNoCreado extends WebApplicationException {
	     public RecursoNoCreado() {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso no se pudo crear por superposicion de horario").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	public class RecursoNoEncontrado extends WebApplicationException {
	     public RecursoNoEncontrado() {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso solicitado no se encontro ").type(MediaType.TEXT_PLAIN).build());
	     }
	}
}
