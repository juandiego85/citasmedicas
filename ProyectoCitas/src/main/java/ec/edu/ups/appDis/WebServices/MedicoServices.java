package ec.edu.ups.appDis.WebServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ec.edu.ups.appDis.DAO.EspecialidadDAO;
import ec.edu.ups.appDis.DAO.MedicoDAO;
import ec.edu.ups.appDis.DAO.UsuarioDAO;
import ec.edu.ups.appDis.Modelo.Medico;
import ec.edu.ups.appDis.Modelo.Usuario;

@Path("/medicos")
public class MedicoServices {

	@Inject MedicoDAO medDao;
	@Inject UsuarioDAO usrDao;
	@Inject EspecialidadDAO espDao;
	
	@GET
	@Path("")
	@Produces ("application/json")
	public List<Map> listarMedicos(
			@QueryParam("sesion") String sesion
			) {
		
		ArrayList<Map> respuesta = new ArrayList<Map>();
		for(Medico medicos : medDao.listadomedicos()) {
			respuesta.add(medicoEntry(medicos));
		}
		System.out.println("la sesion es "+sesion);
		return respuesta;
	}
	
	
	private Map medicoEntry(Medico m) {
		HashMap<String, Object>e=new HashMap<>();
		e.put("identrymedico", m.getMedId());
		e.put("nombre",m.getUsuario().getUsNombre());
		e.put("apellido",m.getUsuario().getUsApellido());
		e.put("especialidadMedico",m.getEspecialidad().getEspnombreEspecialidad());
		
		return e;
	}


	private static class MedicoEntry {
		
		private int identrymedico;
		private String cedula;
		private String nombre;
		private String apellido;
		private String telefono;
		private String correo;
		private String clave;
		//
		private String especialidadMedico;
		
		public MedicoEntry() {
			
		}
		public MedicoEntry(Medico m) {
			identrymedico=m.getMedId();
			nombre = m.getUsuario().getUsNombre();
			apellido = m.getUsuario().getUsApellido();
			especialidadMedico = m.getEspecialidad().getEspnombreEspecialidad();
		}
		
		
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getCorreo() {
			return correo;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public String getEspecialidadMedico() {
			return especialidadMedico;
		}
		public void setEspecialidadMedico(String especialidadMedico) {
		this.especialidadMedico = especialidadMedico;
		}
		public void setCedula(String cedula) {
			this.cedula = cedula;
		}
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}
		public String getClave() {
			return clave;
		}
		public void setClave(String clave) {
			this.clave = clave;
		}
		public int getIdentrymedico() {
			return identrymedico;
		}

	}
	
	
	
	@POST
	@Path("")
	@Produces ("application/json")
	@Consumes ("application/json")
	public ResultEntry guardarMedico(MedicoEntry e) {
		Medico medicos = new Medico();
		medicos.setUsuariousid( new Usuario() );
		medicos.getUsuario().setUsCedula(e.cedula);
		medicos.getUsuario().setUsNombre(e.nombre);
		medicos.getUsuario().setUsApellido(e.apellido);
		medicos.getUsuario().setUsTelefono(e.telefono);
		medicos.getUsuario().setUsCorreo(e.correo);
		medicos.getUsuario().setUsClave(e.clave);
		medicos.setEspecialidad(espDao.leerEspecialidad(e.especialidadMedico));
		if(usrDao.insertarUsuario(medicos.getUsuario()) && medDao.insertarMedico(medicos)) {
			System.out.println("entrando aqui insertando medico");
			return new ResultEntry("OK");
		}else {
			
			return new ResultEntry("FALLO");
		}
	}
	
}
