package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Calendario;
import entidades.Usuario;

public class CalendarioDAO implements DAO<Calendario,Integer>{

	public static CalendarioDAO daoCalendario;

	private CalendarioDAO(){}

	public static CalendarioDAO getInstance() {
		if(daoCalendario==null)
			daoCalendario=new CalendarioDAO();
		return daoCalendario;
	}

	public Calendario persist(String nombreCalendario, int dniUser) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );	
		Usuario user = em.find(Usuario.class, dniUser);
		Calendario calendario = new Calendario(nombreCalendario,user);
		user.addCalendario(calendario);
		em.persist(user);
		Calendario result = em.find(Calendario.class, calendario.getId());
		em.getTransaction().commit();
		
		if(result!=null)return result;
		return null;
	}

	@Override
	public Calendario findById(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		Calendario calendario=entityManager.find(Calendario.class, id);
		entityManager.close();
		return calendario;
	}

	@Override
	public List<Calendario> findAll() {
		EntityManager em=EMF.createEntityManager();
		Query q=em.createNamedQuery(Calendario.BUSCAR_TODOS);
		return q.getResultList();
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Calendario.class, id));
		entityManager.getTransaction().commit();
		Calendario calendario= entityManager.find(Calendario.class, id);
		entityManager.close();
		
		
		if(calendario==null)return true;
		return false;
	}

	@Override
	public Calendario persist(Calendario entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Calendario update(int id, String nombre, int dniUser) {
		EntityManager entityManager=EMF.createEntityManager();
		
		Calendario calendario= entityManager.find(Calendario.class, id);
		
		if(calendario!=null) {
			entityManager.getTransaction().begin();
			calendario.setNombre(nombre);
			calendario.setDuenio(entityManager.find(Usuario.class, dniUser));
			entityManager.persist(calendario);
			entityManager.getTransaction().commit();
			entityManager.close();
			return calendario;
		}
		return null;
	}

	@Override
	public Calendario update(Integer id, Calendario newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
