package servicios;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.ObjectNode;

import entidades.Calendario;
import login.Secured;

@Path("/calendarios")
public class CalendarioREST {

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Calendario> getAllUser(){
		return CalendarioDAO.getInstance().findAll();
	}

	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Calendario calendariofindById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Calendario calendario= CalendarioDAO.getInstance().findById(id);
		if(calendario!=null)
			return calendario;
		else
			return null;
		//				throw new RecursoNoExiste(id);
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCalendario(ObjectNode json) {
		String nombre = json.get("nombre").toString().replaceAll("\"", "");
		int dniUser = json.get("dniuser").intValue();
		Calendario result= CalendarioDAO.getInstance().persist(nombre,dniUser);
		if(result==null) {
			return null;
			//			throw new RecursoNoCreado(calendario.getNombre());
		}else {
			return Response.status(201).entity(result).build();
		}
	}

	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCalendario(@PathParam("id") int id) {
		boolean result= CalendarioDAO.getInstance().delete(id);
		if(result) return Response.status(201).build();
		return null;
		//			throw new RecursoNoExiste(id);
	}

	@PUT
	@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCalendario(@PathParam("id") int id,ObjectNode json) {
		String nombre = json.get("nombre").toString().replaceAll("\"", "");
		int dniUser = json.get("duenio").intValue();
		
		Calendario result = CalendarioDAO.getInstance().update(id, nombre,dniUser);
		if(result!=null) return Response.status(201).entity(result).build();
		//			throw new RecursoNoExiste(id);
		return null;
	}

}


