package ec.edu.ups.appDis.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column
    private String id;
 
    @NotNull
    @ManyToOne
    private Usuario usuario;

	public String getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
        
}
