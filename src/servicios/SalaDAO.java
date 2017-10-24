package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Calendario;
import entidades.Sala;
import entidades.Usuario;


public class SalaDAO implements DAO<Sala,Integer>{

	public static SalaDAO daoSala;

	private SalaDAO(){}

	public static SalaDAO getInstance() {
		if(daoSala==null)
			daoSala=new SalaDAO();
		return daoSala;
	}

	@Override
	public Sala persist(Sala sala) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(sala);
		entityManager.getTransaction().commit();
		entityManager.close();
		return sala;
	}

	@Override
	public Sala update(Integer id, Sala newSala) {
		EntityManager entityManager=EMF.createEntityManager();

		Sala sala = entityManager.find(Sala.class, id);

		if(sala!=null) {
			entityManager.getTransaction().begin();
			sala.setDescripcion(newSala.getDescripcion());
			sala.setNombre(newSala.getNombre());
			entityManager.persist(sala);
			entityManager.getTransaction().commit();
			entityManager.close();
			return sala;
		}
		return null;
	}

	@Override
	public Sala findById(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		Sala sala=entityManager.find(Sala.class, id);
		entityManager.close();
		return sala;
	}

	@Override
	public List<Sala> findAll() {
		EntityManager em=EMF.createEntityManager();
		Query q=em.createNamedQuery(Sala.BUSCAR_TODOS);
		return q.getResultList();
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Sala.class, id));
		entityManager.getTransaction().commit();
		Sala sala = entityManager.find(Sala.class, id);
		entityManager.close();

		
		if(sala==null)return true;
		return false;
	}
}

