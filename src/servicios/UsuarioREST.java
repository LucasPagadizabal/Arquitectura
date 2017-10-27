package servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import entidades.Reunion;
import entidades.Usuario;
import login.Secured;
import servicios.ReunionREST.RecursoNoEncontrado;


@Path("/usuarios")
public class UsuarioREST {
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getAllUser(){
		return UsuarioDAO.getInstance().findAll();
	}
	
	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUserById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario user = UsuarioDAO.getInstance().findById(id);
		if(user!=null)
			return user;
		else
			throw new RecursoNoExiste(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Usuario user) {
		Usuario result= UsuarioDAO.getInstance().persist(user);
		if(result==null) {
			throw new RecursoNoCreado(user.getDni());
		}else {
			return Response.status(201).entity(user).build();
		}
	}
	
	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("id") int id) {
		boolean result= UsuarioDAO.getInstance().delete(id);
		if(result) return Response.status(201).build();
		
		throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("id") int id,Usuario user) {
		Usuario result = UsuarioDAO.getInstance().update(id, user);
		if(result!=null) return Response.status(201).entity(result).build();
		throw new RecursoNoExiste(id);
	}
	
	public class RecursoNoExiste extends WebApplicationException {
	     public RecursoNoExiste(int id) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	public class RecursoNoCreado extends WebApplicationException {
	     public RecursoNoCreado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con ID "+id+" no se pudo crear").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	@GET
	@Path("/getNickName")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario login(@QueryParam("nickName") String nickname, @QueryParam("password") String pass) {
		
		Usuario aux = new Usuario();
		aux.setNickName(nickname);
		aux.setPassword(pass);
		Usuario user = UsuarioDAO.getInstance().login(aux);
		if(user!=null)
			return user;
		else
			throw new RecursoNoExiste(1);
	}

}
