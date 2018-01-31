package ec.edu.ups.appDis.Controller;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.appDis.DAO.UsuarioDAO;
import ec.edu.ups.appDis.Modelo.Sesion;
import ec.edu.ups.appDis.Modelo.Usuario;

@ManagedBean(name="login")
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject UsuarioDAO usrDAO;
	
	private String usuario;
	private String clave;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public String login() {
		
		Usuario u=usrDAO.leerUsuario(usuario);
		if (u == null || u.login(clave)==false) {
			System.out.println("falllllllllllloooooooooooooooooooo"+(u==null));
			System.out.println("usuario" + usuario + "clave"+ clave);
			
			return "login";
		}else
		{
			System.out.println("exitosoooooooooooooooooooooooooooooooooooooo");
			Sesion sesion = new Sesion();
			sesion.setId(UUID.randomUUID().toString());
			sesion.setUsuario(u);
			Map<String, Object> face = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 
			face.put("usrid", sesion.getId());
			usrDAO.insertarSesion(sesion);
			return "index";
		}
		
	}
	 public String error() {
		 Map<String, Object> face = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		 if (face.get("error")==null) {
			return " ";
		}
		 else {
		 return "Debe estar logueado para iniciar";
		 }
	 }
	
}
