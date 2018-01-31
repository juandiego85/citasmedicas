package ec.edu.ups.appDis.WebServices;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ec.edu.ups.appDis.DAO.PacienteDAO;
import ec.edu.ups.appDis.DAO.UsuarioDAO;
import ec.edu.ups.appDis.Modelo.Paciente;
import ec.edu.ups.appDis.Modelo.Usuario;


@Path("/pacientes")
public class PacienteServices {
	
	@Inject PacienteDAO pacDao;
	@Inject UsuarioDAO usrDao;
	
	@GET
	@Path("")
	@Produces ("application/json")
	public List<PacienteEntry> listarPacientes() {
		
		ArrayList<PacienteEntry> respuesta = new ArrayList<PacienteEntry>();
		for(Paciente pacientes : pacDao.listadopacientes()) {
			respuesta.add(new PacienteEntry(pacientes));
		}
		return respuesta;
	}
	
	
	private static class PacienteEntry {
		
		private int identrypaciente;
		private String cedula;
		private String nombre;
		private String apellido;
		private String telefono;
		private String correo;
		private String clave;
		//
		
		public PacienteEntry() {
			
		}
		public PacienteEntry(Paciente p) {
			identrypaciente=p.getPacId();
			nombre=p.getUsuario().getUsNombre();
			apellido=p.getUsuario().getUsApellido();
		}
		public String getCedula() {
			return cedula;
		}
		public void setCedula(String cedula) {
			this.cedula = cedula;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
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
		public int getIdentrypaciente() {
			return identrypaciente;
		}
		public void setClave(String clave) {
			this.clave = clave;
		}

	}
	
	
	
	@POST
	@Path("")
	@Produces ("application/json")
	@Consumes ("application/json")
	public ResultEntry guardarPaciente(PacienteEntry e) {
		Paciente pacientes = new Paciente();
		pacientes.setUsuariousid(new Usuario());
		pacientes.getUsuario().setUsCedula(e.cedula);
		pacientes.getUsuario().setUsNombre(e.nombre);
		pacientes.getUsuario().setUsApellido(e.apellido);
		pacientes.getUsuario().setUsTelefono(e.telefono);
		pacientes.getUsuario().setUsCorreo(e.correo);
		pacientes.getUsuario().setUsClave(e.clave);
		if(usrDao.insertarUsuario(pacientes.getUsuario()) && pacDao.insertarPaciente(pacientes)) {
			System.out.println("entrando aqui insertando paciente");
			return new ResultEntry("OK");
		}else {
			
			return new ResultEntry("FALLO");
		}
	}

}
