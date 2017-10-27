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

import entidades.Sala;
import entidades.Usuario;


@Path("/salas")
public class SalaREST {
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sala findById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Sala sala= SalaDAO.getInstance().findById(id);
		if(sala!=null)
			return sala;
		else throw new RecursoNoEncontrado(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sala> getAllUser(){
		return SalaDAO.getInstance().findAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Sala sala) {
		Sala result= SalaDAO.getInstance().persist(sala);
		if(result==null) {
			throw new RecursoNoCreado();
		}else {
			return Response.status(201).entity(sala).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSala(@PathParam("id") int id) {
		boolean result= SalaDAO.getInstance().delete(id);
		if(result) return Response.status(201).build();
		throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSala(@PathParam("id") int id,Sala sala) {
		Sala result = SalaDAO.getInstance().update(id, sala);
		if(result!=null) return Response.status(201).entity(result).build();
//		throw new RecursoNoExiste(id);
		return null;
	}
	
	public class RecursoNoCreado extends WebApplicationException {
	     public RecursoNoCreado() {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso no se pudo crear por superposicion de horario").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	public class RecursoNoEncontrado extends WebApplicationException {
	     public RecursoNoEncontrado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con el id:"+id+" solicitado no se encontro ").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	public class RecursoNoExiste extends WebApplicationException {
	     public RecursoNoExiste(int id) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
	     }
	}
}
