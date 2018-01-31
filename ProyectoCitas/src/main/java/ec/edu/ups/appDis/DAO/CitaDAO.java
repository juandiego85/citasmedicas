package ec.edu.ups.appDis.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ec.edu.ups.appDis.Modelo.Cita;
import ec.edu.ups.appDis.Modelo.Usuario;

@Stateful
public class CitaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;
	@Inject 
	private EntityManager em;
	
	
	public boolean insertarCita(Cita cita) {
		try {
			System.out.println("Insertando al Cita");
			em.persist(cita);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR al Insertar /////////////// Cita");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean actualizarCita (Cita cita) {
		try {
			em.merge(cita);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public void eliminarCita(int citId) {
			Cita cita = em.find(Cita.class, citId);
			em.remove(cita);
	}
	
	public Cita leerCita(int citId) {
		return em.find(Cita.class, citId);
	}
	
	public List<Cita> listacitas(){
		String sql = "SELECT ci FROM Cita ci ";
		Query query = em.createQuery(sql, Cita.class);
		List<Cita> listadocitas = query.getResultList();
		
		return listadocitas;
	}

	public void guardarCitas(Cita cita) {
		
		if( cita.getCitId() == null)
			insertarCita(cita);
		else
			actualizarCita(cita);
	}

	public List<Cita> listasCitasUsuario(int id) {
		String sql = "SELECT ci FROM Cita ci where ci.pacientepacid.usuario.id=:id or ci.medicomedid.usuario.id=:id";
		Query query = em.createQuery(sql, Cita.class);
		query.setParameter("id", id);
		List<Cita> listadocitas = query.getResultList();
		
		return listadocitas;
	}
}

