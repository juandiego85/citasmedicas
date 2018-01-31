package ec.edu.ups.appDis.DAO;

import java.util.List;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ec.edu.ups.appDis.Modelo.Especialidad;
import ec.edu.ups.appDis.Modelo.Medico;
import ec.edu.ups.appDis.Modelo.Sesion;
import ec.edu.ups.appDis.Modelo.Usuario;

@Stateful
public class UsuarioDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;
	@Inject 
	private EntityManager em;
	
	public boolean insertarUsuario(Usuario usuario) {
		try {
			System.out.println("Insertando a la usuario");
			em.persist(usuario);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR al Insertar /////////////// usuario");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void actualizarUsuario (Usuario usuario) {
			em.merge(usuario);
	}
	
	public void eliminarUsuario(int usID) {
			Usuario usuario = em.find(Usuario.class, usID);
			em.remove(usuario);
	}
	
	public Usuario leerUsuario(int usID) {
		return em.find(Usuario.class, usID);
	}
	
	public Usuario leerUsuario(String usrCorreo) {
		try {
					String sql = "SELECT usr FROM Usuario usr where usr.usCorreo =:correo";
					Query query = em.createQuery(sql, Usuario.class);
					query.setParameter("correo", usrCorreo);
					Usuario usuario = (Usuario) query.getSingleResult();
		
					return usuario;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}

	}
	
	public void actualizar2Usuario(Usuario usuario, Medico medico) {
		em.merge(medico);
		em.merge(usuario);
	}
	
	public List<Usuario> listadoUsuarios(){
		String sql = "SELECT u FROM Usuario u ";
		Query query = em.createQuery(sql, Usuario.class);
		List<Usuario> listadousuarios = query.getResultList();
		
		return listadousuarios;
	}

	public void guardarUsuario(Usuario usuario) {
	
		if( usuario.getUsId() == null)
			insertarUsuario(usuario);
		else
			actualizarUsuario(usuario);
	}

	public Usuario leerUsuarioPorSesion(String sesion) {
		try {
			String sql = "SELECT s.usuario FROM Sesion s where s.id =:sesion";
			Query query = em.createQuery(sql, Usuario.class);
			query.setParameter("sesion", sesion);
			Usuario usuario = (Usuario) query.getSingleResult();

			return usuario;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}

	public boolean insertarSesion(Sesion sesion) {
		// TODO Auto-generated method stub
		try {
			em.persist(sesion);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR al Insertar /////////////// sesion");
			e.printStackTrace();
			return false;
		}
	}
	

}
