package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Calendario;
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
		Query q=em.createNamedQuery(Usuario.BUSCAR_LOGIN);
		return (Usuario) q.getResultList();
	}
}
