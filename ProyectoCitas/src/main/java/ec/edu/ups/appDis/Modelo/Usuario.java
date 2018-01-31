package ec.edu.ups.appDis.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.ext.ParamConverter.Lazy;

import org.hibernate.validator.constraints.Email;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "us_id")
    private Integer usId;
   
    @NotNull
    @Size(min = 10, max = 10)
    @Column(unique=true, name = "us_cedula")
    private String usCedula;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "us_nombre")
    private String usNombre;

    @NotNull
    
    @Size(min = 3, max = 20)
    @Column(name = "us_apellido")
    private String usApellido;

    @NotNull
    @Size(min = 7, max = 10)
    @Digits(fraction=0, integer=12)
    @Column(name = "us_telefono")
    private String usTelefono;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(unique=true, name = "us_correo")
    @Email
    private String usCorreo;

    @NotNull
    @Size(min = 6, max = 15)
    @Column(name = "us_clave")
    private String usClave;

  //bi-directional many-to-one association to paciente
    @OneToOne(mappedBy = "usuario")
    private Paciente paciente ;
    
  //bi-directional many-to-one association to medico
    @OneToOne( mappedBy = "usuario")
    private Medico medico ;
    
    @OneToMany
    private List<Sesion> sesionList;
    
    
    public List<Sesion> getSesionList() {
		return sesionList;
	}



	public void setSesionList(List<Sesion> sesionList) {
		this.sesionList = sesionList;
	}



	public Usuario() {
    }

    
    
	public Paciente getPaciente() {
		return paciente;
	}



	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}



	public Medico getMedico() {
		return medico;
	}



	public void setMedico(Medico medico) {
		this.medico = medico;
	}



	public Integer getUsId() {
		return usId;
	}

	public void setUsId(Integer usId) {
		this.usId = usId;
	}

	public String getUsCedula() {
		return usCedula;
	}

	public void setUsCedula(String usCedula) {
		this.usCedula = usCedula;
	}

	public String getUsNombre() {
		return usNombre;
	}

	public void setUsNombre(String usNombre) {
		this.usNombre = usNombre;
	}

	public String getUsApellido() {
		return usApellido;
	}

	public void setUsApellido(String usApellido) {
		this.usApellido = usApellido;
	}

	public String getUsTelefono() {
		return usTelefono;
	}

	public void setUsTelefono(String usTelefono) {
		this.usTelefono = usTelefono;
	}

	public String getUsCorreo() {
		return usCorreo;
	}

	public void setUsCorreo(String usCorreo) {
		this.usCorreo = usCorreo;
	}

	public void setUsClave(String usClave) {
		this.usClave = usClave;
	}

	public String getUsClave() {
		return usClave;
	}

	public boolean login(String clave) {
		// TODO Auto-generated method stub
		boolean r = this.usClave.equals(clave);
		System.out.println("login " + r);
		return r;
	}

	@Override
	public String toString() {
		return "Usuario [usId=" + usId + ", usCedula=" + usCedula + ", usNombre=" + usNombre + ", usApellido="
				+ usApellido + ", usTelefono=" + usTelefono + ", usCorreo=" + usCorreo + ", usClave=" + usClave
				+ ", paciente=" + paciente + ", medico=" + medico + "]";
	}
}
