package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Calendario;
import entidades.Notificacion;
import entidades.Reunion;
import entidades.Sala;
import entidades.Usuario;

public class UsuarioDAO implements DAO<Usuario,Integer>{

	public static UsuarioDAO daoUser;
	
	private UsuarioDAO(){}
	
	public static UsuarioDAO getInstance() {
		if(daoUser==null)
			daoUser=new UsuarioDAO();
		return daoUser;
	}
	
	
	@Override
	public List<Usuario> findAll() {
		EntityManager em=EMF.createEntityManager();
		Query q=em.createNamedQuery(Usuario.BUSCAR_TODOS);
		return q.getResultList();
	}

	@Override
	public Usuario findById(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		Usuario user=entityManager.find(Usuario.class, id);
		entityManager.close();
		return user;
	}
	
	
	@Override
	public Usuario persist(Usuario user) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		user.addCalendario(new Calendario("Mi Calendario "+user.getApellido(),user));
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
		return user;
	}

	@Override
	public Usuario update(Integer id, Usuario userUpdate) {
		EntityManager entityManager=EMF.createEntityManager();
		
		Usuario user = entityManager.find(Usuario.class, id);
		
		if(user!=null) {
			entityManager.getTransaction().begin();
			user.setNombre(userUpdate.getNombre());
			user.setApellido(userUpdate.getApellido());
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			entityManager.close();
			return user;
		}
		return null;
	}
	

	@Override
	public boolean delete(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Usuario.class, id));
		entityManager.getTransaction().commit();
		Usuario user = entityManager.find(Usuario.class, id);
		entityManager.close();
		
		
		if(user==null)return true;
		return false;
	}

	public static Usuario login(Usuario user) {
		EntityManager em=EMF.createEntityManager();
		Query query=em.createNamedQuery(Usuario.BUSCAR_LOGIN);
		query.setParameter(1, user.getNickName());
		query.setParameter(2, user.getPassword());
		return (Usuario) query.getSingleResult();
	}
	
	//servicios no REST
	public static void addInvitado(int dni,int id_reunion) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );
		Usuario user = em.find(Usuario.class, dni);
		Reunion reunion = em.find(Reunion.class, id_reunion);

		if(user !=null && reunion != null) {	
			Notificacion noti = new Notificacion(user,reunion);
			user.addNotificacion(noti);
			reunion.addInvitado(user);
			em.persist(noti);
			em.persist(user);
			em.persist(reunion);
		}
		em.getTransaction().commit();
	}

	public static void aceptarInvitacion(int dni, int id_noti) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );
		Usuario user = em.find(Usuario.class, dni);
		Notificacion noti = em.find(Notificacion.class, id_noti);

		if(user!=null && noti!=null) {
			if(user.existeInvitacion(noti)) {
				user.addInvitacion(noti.getReunion());
				em.remove(noti);
				em.persist(user);
				em.flush();
			}
		}
		em.getTransaction().commit();
	}

	public static void compartirCalendario(int id_calendario, int dni_invitado) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );

		Usuario user = em.find(Usuario.class, dni_invitado);
		Calendario calendario = em.find(Calendario.class, id_calendario);
		if(user != null && calendario !=null) {
			user.addCalendario(calendario);
			em.persist(user);
			em.persist(calendario);
		}

		em.getTransaction().commit();
	}
	
	public static void restaurarBD() {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );	
		em.createNamedQuery(Usuario.BORRAR_DATOS).executeUpdate();
		em.createNamedQuery(Calendario.BORRAR_DATOS).executeUpdate();
		em.createNamedQuery(Reunion.BORRAR_DATOS).executeUpdate();
		em.createNamedQuery(Sala.BORRAR_DATOS).executeUpdate();
		em.createNamedQuery(Notificacion.BORRAR_DATOS).executeUpdate();
		em.getTransaction().commit();
	}
}
