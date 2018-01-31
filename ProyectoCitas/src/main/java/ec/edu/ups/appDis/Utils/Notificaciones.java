package ec.edu.ups.appDis.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ec.edu.ups.appDis.Modelo.Usuario;

public class Notificaciones {

	public static ArrayList<Map<String, Object>> notificaciones = new ArrayList<>();
	
	public static void notifica(Usuario usuario, String mensaje) {
		// TODO Auto-generated method stub
		HashMap<String , Object > n= new HashMap();
		n.put("fecha", new Date());
		n.put("mensaje",mensaje);
		n.put("usuario",usuario.getUsId());
		notificaciones.add(n);
		
	}

}
