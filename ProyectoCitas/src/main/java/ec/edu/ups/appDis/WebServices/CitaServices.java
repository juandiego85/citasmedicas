package ec.edu.ups.appDis.WebServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ec.edu.ups.appDis.DAO.CitaDAO;
import ec.edu.ups.appDis.DAO.MedicoDAO;
import ec.edu.ups.appDis.DAO.PacienteDAO;
import ec.edu.ups.appDis.DAO.UsuarioDAO;
import ec.edu.ups.appDis.Modelo.Cita;
import ec.edu.ups.appDis.Modelo.Medico;
import ec.edu.ups.appDis.Modelo.Paciente;
import ec.edu.ups.appDis.Modelo.Usuario;
import ec.edu.ups.appDis.Utils.Notificaciones;


@Path("/citas")
public class CitaServices {
	
	@Inject CitaDAO citaDao;
	@Inject MedicoDAO medDao;
	@Inject PacienteDAO pacDao;
	@Inject UsuarioDAO usrDao;
	
	
	
		
	private Map citaEntry(Cita cita){
			HashMap<String, Object> c= new HashMap<String, Object>();
			c.put("id", cita.getCitId());
			c.put("doctor",cita.getMedicomedid().toString());
			c.put("paciente",cita.getPacientepacid().toString());
			c.put("fecha",cita.getCitFecha());
			c.put("estado",cita.getCitEstado());
			
			return c;
		}
	
	
	@GET
	@Path("")
	@Produces ("application/json")
	public List<Map> listarCitas(
			@QueryParam("sesion") String sesion
			) {
		
		ArrayList<Map> respuesta = new ArrayList<>();
		Usuario user=  usrDao.leerUsuarioPorSesion(sesion);
		
		for(Cita cita :citaDao.listasCitasUsuario(user.getUsId())) {
			if (cita.getCitEstado()==Cita.PENDIENTE) {
				respuesta.add(citaEntry(cita));
			}
		}
		return respuesta;
	}
	
	@POST
	@Path("")
	@Produces ("application/json")
	@Consumes ("application/json")
	public Map<String, String> crearCita(HashMap<String, Object>map) {
		int iddoctor = (int) map.get("doctor");

		Date fechaC = new Date((long) map.get("fecha"));
		System.out.println("la fecha es"+fechaC);
		Medico medico= medDao.leerMedico(iddoctor);
		String sesion = (String) map.get("sesion");
		Usuario u=usrDao.leerUsuarioPorSesion(sesion);
		
		Paciente paciente=u.getPaciente();
		Cita citas = new Cita ();
		citas.setMedicomedid(medico);
		citas.setPacientepacid(paciente);
		citas.setCitEstado(Cita.PENDIENTE);
		citas.setCitFecha(fechaC);
		Notificaciones.notifica (citas.getPacientepacid().getUsuario(),"tiene una nueva cita, el dia"+citas.getCitFecha());
		
		return Collections.singletonMap("estado", 
				citaDao.insertarCita(citas)?"ok":"Fallo"		
				
				);
	}
	
	
	@GET
	@Path("/n")
	@Produces ("application/json")
	public List<Map<String, Object>> notificaciones(
			@QueryParam("desde") String desde,
			@QueryParam("sesion") String sesion
			){
		
		Date fDesde =new Date(Long.parseLong(desde));
		
	
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Usuario u=usrDao.leerUsuarioPorSesion(sesion);
		
		
		System.out.println("Su sesion essssssssssss"+sesion);
		
		ArrayList<Map<String, Object>> notificacionList = new ArrayList<>();
		for(Map<String, Object> n:Notificaciones.notificaciones) {
			Date fecha= (Date) n.get("fecha");
			int uid=(int) n.get("usuario");
			if(
				fecha.after(fDesde) &&
				u.getUsId()==uid
			) {
				notificacionList.add(n);
			}
		}
		return notificacionList; 
	}
	
	@POST
	@Path("/cancelar")
	@Produces ("application/json")
	@Consumes ("application/json")
	public Map<String, String> cancelarCita(HashMap<String, Object>map) {
		int citaId=(int) map.get("id");
		
		Cita cita = citaDao.leerCita(citaId);
		cita.setCitEstado(Cita.CANCELADA);
		
		if (citaDao.actualizarCita(cita)==false) {
			return Collections.singletonMap("estado", "Fallo");
		}
		
		Usuario u= usrDao.leerUsuarioPorSesion((String) map.get("sesion"));
		if (u.getUsId()==cita.getPacientepacid().getUsuario().getUsId()) {
			Notificaciones.notifica (cita.getMedicomedid().getUsuario(),"Se ha cancelado la cita del dia"+cita.getCitFecha());
		}else {
				Notificaciones.notifica (cita.getPacientepacid().getUsuario(),"Se ha cancelado la cita del dia"+cita.getCitFecha());
		}
		
		return Collections.singletonMap("estado", "ok");
	}
	
	@POST
	@Path("/falto")
	@Produces ("application/json")
	@Consumes ("application/json")
	public Map<String, String> faltoCita(HashMap<String, Object>map) {
		int citaId=(int) map.get("id");
		
		Cita cita = citaDao.leerCita(citaId);
		cita.setCitEstado(Cita.FALTO);
		
		if (citaDao.actualizarCita(cita)==false) {
			return Collections.singletonMap("estado", "Fallo");
		}
		Notificaciones.notifica (cita.getPacientepacid().getUsuario(),"Usted falto a la cita del dia"+cita.getCitFecha());
		
		return Collections.singletonMap("estado", "ok");
	}
	@POST
	@Path("/atendio")
	@Produces ("application/json")
	@Consumes ("application/json")
	public Map<String, String> atendioCita(HashMap<String, Object>map) {
		int citaId=(int) map.get("id");
	
		Cita cita = citaDao.leerCita(citaId);
		cita.setCitEstado(Cita.ATENDIDO);
		cita.setCitReceta((String) map.get("receta"));
		if (citaDao.actualizarCita(cita)==false) {
			return Collections.singletonMap("estado", "Fallo");
		}
		Notificaciones.notifica (cita.getPacientepacid().getUsuario(),"Su receta es"+cita.getCitReceta());
		
		return Collections.singletonMap("estado", "ok");
	}
}
