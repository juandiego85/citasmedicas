package ec.edu.ups.appDis.WebServices;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ec.edu.ups.appDis.DAO.UsuarioDAO;
import ec.edu.ups.appDis.Modelo.Sesion;
import ec.edu.ups.appDis.Modelo.Usuario;

@Path("/login")
public class LoginServices {

	@Inject UsuarioDAO usrDAO;
	
	
	private static class LoginEntry{
		private String correo;
		private String clave;
		
		
		public String getCorreo() {
			return correo;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public String getClave() {
			return clave;
		}
		public void setClave(String clave) {
			this.clave = clave;
		}
	}
	

	@POST
	@Path("")
	@Produces ("application/json")
	@Consumes ("application/json")
	public Map iniciarSesion(LoginEntry e) {
		HashMap<String, Object>m=new HashMap<>();
		Usuario u = usrDAO.leerUsuario(e.correo);
		if (u == null || u.login(e.clave)==false) {
			m.put("resul", "fallo");
			
		}else
		{
			Sesion sesion = new Sesion();
			sesion.setId(UUID.randomUUID().toString());
			sesion.setUsuario(u);
			usrDAO.insertarSesion(sesion);
			m.put("resul", sesion.getId());
			m.put("nombre", u.getUsNombre()+" "+ u.getUsApellido());
			m.put("tipo", u.getMedico()==null?"paciente":"medico");
			
		}
		return m;
	}
	
}
