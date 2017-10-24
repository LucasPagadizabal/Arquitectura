package entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.NamedQuery;

@NamedQuery( name= Calendario.BUSCAR_TODOS ,query = "SELECT c FROM Calendario c")
@NamedQuery( name= Calendario.BORRAR_DATOS ,query = "DELETE FROM Calendario c")

@Entity
@Table(name="Calendario")
public class Calendario {

	public static final String BORRAR_DATOS = "Calendario.borrarDatos";
	public static final String BUSCAR_TODOS = "Calendario.buscarTodos";
	
	@Id
	@GeneratedValue
	private int id;
	private String nombre;
	
	@ManyToOne
	private Usuario duenio;
	
	@OneToMany(mappedBy="calendario",cascade=CascadeType.PERSIST)
	private List<Reunion> reuniones;

	public Calendario() {}

	public Calendario(String nombre,Usuario duenio) {
		this.nombre = nombre;
		this.duenio = duenio;
		this.reuniones = new ArrayList<Reunion>();
	}
	
	public boolean addReunion(Reunion reunion) {
		if(!this.checkSuperPosicion(reunion.getFechaInicio(), reunion.getFechaFin())) {
			return this.reuniones.add(reunion);
		}
		return false;
	}
	
	public boolean checkSuperPosicion(Date fechaI, Date fechaF) {
		if(this.reuniones.size()>0) {
			for (int i = 0; i < this.reuniones.size(); i++) {
				if(this.reuniones.get(i).superposicionHorarios(fechaI, fechaF)) {
					return true;
				}
			}
		}	
		return false;
	}
	
	public boolean removeReunion(Reunion reunion) {
		return this.reuniones.remove(reunion);
	}
	
	public boolean equals(Calendario cal) {
		return this.id == cal.getId();
	}

	//	Getters and Setters
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getDuenio() {
		return duenio;
	}

	public void setDuenio(Usuario duenio) {
		this.duenio = duenio;
	}
}
