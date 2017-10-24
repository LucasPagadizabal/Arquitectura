package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.NamedQuery;


@NamedQuery(name= Usuario.BUSCAR_TODOS,query="SELECT u FROM Usuario u")
@NamedQuery(name= Usuario.BUSCAR_USUARIO_BY_DNI,query = "SELECT u FROM Usuario u WHERE u.dni = ?1")
@NamedQuery(name= Usuario.BORRAR_DATOS,query = "DELETE FROM Usuario u")
@NamedQuery(name= Usuario.BUSCAR_LOGIN,query = "SELECT u FROM Usuario u WHERE u.nickName=?1 AND u.password=?2")

@Entity
public class Usuario {
	
	public static final String BUSCAR_TODOS = "Usuario.buscarTodos";
	public static final String BUSCAR_USUARIO_BY_DNI = "Usuario.buscarUsuarioByDni";
	public static final String BORRAR_DATOS = "Usuario.borrarDatos";
	public static final String BUSCAR_LOGIN = "Usuario.buscarLogin";

	
	@Id
	@GeneratedValue
	private int dni;
	
	private String nombre;
	private String apellido;
	private String nickName;
	private String password;
	
	
	//lista de calendarios
	@ManyToMany(cascade= {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Calendario>calendarios;
	
	//notificaciones pendientes
	@OneToMany(mappedBy="invitado")
	private List<Notificacion> invitaciones;
	
	//reuniones a la que fui invitado
	@ManyToMany
	private List<Reunion>reunionesInvitado;
	
	public Usuario() {}
	
	public Usuario(String nombre,String apellido,String nickName,String password) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.nickName=nickName;
		this.password = password;
		this.calendarios = new ArrayList<Calendario>();
		this.invitaciones = new ArrayList<Notificacion>();
		this.reunionesInvitado = new ArrayList<Reunion>();
	}
	
	public boolean existeInvitacion(Notificacion not) {
		return this.invitaciones.contains(not);
	}
	
	public boolean addInvitacion(Reunion reunion) {
		for (int i = 0; i < this.calendarios.size(); i++) {//busca en todos sus calendario si se superpone con alguna reunion
			if(this.calendarios.get(i).checkSuperPosicion(reunion.getFechaInicio(), reunion.getFechaFin())){
				return false;
			}
		}
		return this.reunionesInvitado.add(reunion);
	}
	
	public boolean addReunionByCalendario(Reunion reunion, Calendario calendario) {
		if(this.calendarios.contains(calendario)) {
				return calendario.addReunion(reunion);
			}
		return false;
	}
	
	public boolean addCalendario(Calendario calendario) {
		if(this.calendarios !=null)return this.calendarios.add(calendario);	
		this.calendarios = new ArrayList<Calendario>();
		return this.calendarios.add(calendario);
	}
	
	public boolean removeCalendario(Calendario calendario) {
		return this.calendarios.remove(calendario);
	}

	
	public boolean addNotificacion(Notificacion notificacion) {
		return this.invitaciones.add(notificacion);
	}
	
	public boolean removeNotificacion(Notificacion notificacion) {
		return this.invitaciones.remove(notificacion);
	}

	public boolean equals(Usuario usuario) {
		return this.dni == usuario.getDni();
	}
	
	public String toString() {
		return "DNI: "+this.dni+"   Nombre: "+this.nombre+"  Apellido: "+this.apellido;
	}
	
//	Getters and Setters
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}
