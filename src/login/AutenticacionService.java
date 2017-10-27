package login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import entidades.Usuario;
import servicios.UsuarioDAO;
import javax.ws.rs.core.Response;

@Path("/autenticacion")
public class AutenticacionService {
  	    @POST
	    @Produces("application/json")
	    @Consumes("application/json")
	    public Response autenticarUser(Credencial credentials){
  	      String username = credentials.getNickName();
	      String password = credentials.getPassword();
	      
	      try {
     
	            autenticar(username, password);
         
	            String token = emitirToken(username);
         
	            return Response.ok(token).build();

	        } catch (Exception e) {
	            return Response.status(Response.Status.UNAUTHORIZED).build();
	        }

  	    }
  	    
	    private void autenticar(String username, String password) throws Exception{
	    	  Usuario user = new Usuario();
			     user.setNickName(username);
			     user.setPassword(password);
			     user = UsuarioDAO.login(user);
			     if(user == null){
			    	 throw new RuntimeException();
			     }

	    }
	    
	    private String emitirToken(String username){
	        String token = TokenHelper.generarToken(username);
	    	TokenHelper.setToken(token, username);
	    	return token;

	    }	

}
