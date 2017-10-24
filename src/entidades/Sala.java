package entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
@NamedQuery( name=Sala.BUSCAR_TODOS ,query ="SELECT s FROM Sala s")
@NamedQuery( name=Sala.BORRAR_DATOS ,query ="DELETE FROM Sala s")

@Entity
@Table(name="Sala")
public class Sala {

	public static final String BORRAR_DATOS= "Sala.borrarDatos";
	public static final String BUSCAR_TODOS= "Sala.buscarTodos";
	
	@Id
	@GeneratedValue
	private int id;
	
	private String nombre;
	
	private String descripcion;
	
	@OneToMany(mappedBy="sala")
	private List<Reunion>reuniones;
	
	public Sala() {}
	
	public Sala(String nombre,String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.reuniones = new ArrayList<Reunion>();
	}
	
	public boolean checkSuperPosicion(Date fechaI,Date fechaF) {
		if(this.reuniones.size()>0) {
			for (int i = 0; i < this.reuniones.size(); i++) {
				if(this.reuniones.get(i).superposicionHorarios(fechaI, fechaF)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean addReunion(Reunion reunion) {
		return this.reuniones.add(reunion);
	}
	
	public boolean equals(Sala sala) {
		return this.id == sala.getId();
	}
//	Getters and Setters
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
