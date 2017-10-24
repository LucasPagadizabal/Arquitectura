package servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Calendario;
import entidades.Reunion;
import entidades.Sala;
import entidades.Usuario;

public class ReunionDAO implements DAO<Reunion,Integer> {
	
public static ReunionDAO daoReunion;
	
	private ReunionDAO(){}
	
	public static ReunionDAO getInstance() {
		if(daoReunion==null)
			daoReunion=new ReunionDAO();
		return daoReunion;
	}
	
	@Override
	public Reunion persist(Reunion reunion) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(reunion);
		entityManager.getTransaction().commit();
		entityManager.close();
		return reunion;
	}

	@Override
	public Reunion update(Integer id, Reunion newReunion) {
		EntityManager entityManager=EMF.createEntityManager();
		
		Reunion reunion = entityManager.find(Reunion.class,id);
		
		if(reunion!=null) {
			entityManager.getTransaction().begin();
			reunion.setCalendario(newReunion.getCalendario());
			reunion.setFechaFin(newReunion.getFechaFin());
			reunion.setFechaInicio(newReunion.getFechaInicio());
			reunion.setLugar(newReunion.getLugar());
			entityManager.persist(newReunion);
			entityManager.getTransaction().commit();
			entityManager.close();
			return newReunion;
		}
		return null;
	}

	@Override
	public Reunion findById(Integer id) {
		EntityManager em=EMF.createEntityManager();
		Reunion reunion =em.find(Reunion.class, id);
		return reunion;
	}

	@Override
	public List<Reunion> findAll() {
		EntityManager em=EMF.createEntityManager();
		Query q=em.createNamedQuery(Reunion.BUSCAR_REUNIONES);
		return q.getResultList();
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Reunion.class, id));
		entityManager.getTransaction().commit();
		entityManager.close();
		
		Reunion reunion= entityManager.find(Reunion.class, id);
		if(reunion==null)return true;
		return false;
	}
		
	public static List<Reunion> getReunionesByUserAndDay(int dni,Date day) {
		EntityManager em=EMF.createEntityManager();
		Query query = em.createNamedQuery(Reunion.BUSCAR_REUNIONES_BY_USER_DAY);
		query.setParameter(1, dni);
		query.setParameter(2, day);
		List<Reunion> result = query.getResultList();
		return result;
	}
	
	public static List<Reunion> getReunionesEntreFechas(Date day1, Date day2){
		EntityManager em=EMF.createEntityManager();
		Query query = em.createNamedQuery(Reunion.BUSCAR_REUNIONES_BETWEEN_DATES);
		query.setParameter(1, day1);
		query.setParameter(2, day2);
		List<Reunion> result = query.getResultList();
		return result;
	}
	
	public static List<Reunion> getReunionesSuperpuestas(int dni,Date newIni,Date newFin){
		EntityManager em=EMF.createEntityManager();
		Query query = em.createNamedQuery(Reunion.BUSCAR_REUNIONES_SUPERPUESTAS);
		query.setParameter(1, dni);
		query.setParameter(2, newFin);
		query.setParameter(3, newIni);
		List<Reunion> result = query.getResultList();
		return result;
	}
	
	public static Reunion createReunion(Date fechaInicio,Date fechaFin,int idSala,int idCalendario) {
		EntityManager em=EMF.createEntityManager();
		
		if(fechaInicio.compareTo(fechaFin)<0) {
		
			Sala lugar = em.find(Sala.class, idSala);//chequear q no se superponga
			Calendario calendario = em.find(Calendario.class, idCalendario);//chequear q no se superoponga con otra reuniones
			
			if(lugar!=null && calendario!=null) {

				if(!lugar.checkSuperPosicion(fechaInicio, fechaFin)){
					if(!calendario.checkSuperPosicion(fechaInicio, fechaFin)){
						em.getTransaction( ).begin();
						Reunion reunion = new Reunion(fechaInicio,fechaFin,lugar,calendario);
						calendario.addReunion(reunion);
						lugar.addReunion(reunion);
						em.persist(reunion);
						em.persist(calendario);
						em.persist(lugar);
						em.getTransaction().commit();
						return reunion;
					}else {
						//mensajes de error superpo calend
						return null;
					}
				}else {
					//mensajes de error superpos reunion
					return null;
				}
			}
			
			return null;
		}
		return null;
	}

}
