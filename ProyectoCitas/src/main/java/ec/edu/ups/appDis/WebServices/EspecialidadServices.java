package ec.edu.ups.appDis.WebServices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ec.edu.ups.appDis.DAO.EspecialidadDAO;
import ec.edu.ups.appDis.Modelo.Especialidad;

@Path("/especialidad")
public class EspecialidadServices {
	
	@Inject EspecialidadDAO espeDao; 
	
	
	/*
	 private static class medicoentry {
	 campos que yo deseo mostrar set y getters}
	 */
	
	@GET
	@Path("")
	@Produces ("application/json")
	public List<String> listarEspecilidad() {
		
		ArrayList<String> respuesta = new ArrayList<String>();
		for(Especialidad especialidad : espeDao.listadoespecialidades()) {
			respuesta.add(especialidad.toString());
		}
		return respuesta;
	}
	
	
	private static class EspecialidadEntry {
		private String nombre;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
	}
	
	
	
	@POST
	@Path("")
	@Produces ("application/json")
	@Consumes ("application/json")
	public String guardarEspecilidad(EspecialidadEntry e) {
		Especialidad especialidad = new Especialidad();
		especialidad.setEspnombreEspecialidad(e.nombre);
		if(espeDao.insertarEspecialidad(especialidad)) {
			System.out.println("entrando aqui");
			return "OK";
		}else {
			
			return "FALLO";
		}
	}
	
	
	@GET
	@Path("/formato")
	@Produces ("application/json")
	public EspecialidadEntry formato() {
			EspecialidadEntry e = new EspecialidadEntry(); 
			e.nombre = "pepe";
			return e;
		
	}
	
	@GET
	@Path("/saludo")
	@Produces ("application/json")
	public String saludo(@QueryParam("n") String nombre) {
		return "hola " + nombre;
	}
	
	@GET
	@Path("/suma")
	@Produces ("application/json")
	public int suma (@QueryParam ("a") int a, @QueryParam("b") int b) {
		return a+b ;
	}

}
