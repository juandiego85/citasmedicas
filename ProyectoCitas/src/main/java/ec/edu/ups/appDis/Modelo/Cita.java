package ec.edu.ups.appDis.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.util.Date;

@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"cit_fecha", "Medico_med_id"})})
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final int PENDIENTE=0;
    public static final int CANCELADA=1;
    public static final int ATENDIDO=2;
    public static final int FALTO=3;	
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "cit_id")
    private Integer citId;
    
    @NotNull
    @Column(name = "cit_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date citFecha;
 
    @NotNull
    @Column(name = "cit_estado")
    private int citEstado;
    
    @NotNull
    @Column(name = "cit_receta")
    private String citReceta="";
    
    @JoinColumn(name = "Medico_med_id", referencedColumnName = "med_id")
    @ManyToOne
    private Medico medicomedid;
    
    @JoinColumn(name = "Paciente_pac_id", referencedColumnName = "pac_id")
    @ManyToOne
    private Paciente pacientepacid;

    public Cita() {
    }

	public Integer getCitId() {
		return citId;
	}

	public void setCitId(Integer citId) {
		this.citId = citId;
	}

	public Date getCitFecha() {
		return citFecha;
	}

	public void setCitFecha(Date citFecha) {
		this.citFecha = citFecha;
	}

	public int getCitEstado() {
		return citEstado;
	}

	public void setCitEstado(int citEstado) {
		this.citEstado = citEstado;
	}

	public Medico getMedicomedid() {
		return medicomedid;
	}

	public void setMedicomedid(Medico medicomedid) {
		this.medicomedid = medicomedid;
	}

	public Paciente getPacientepacid() {
		return pacientepacid;
	}

	public void setPacientepacid(Paciente pacientepacid) {
		this.pacientepacid = pacientepacid;
	}

	
	public String getCitReceta() {
		return citReceta;
	}

	public void setCitReceta(String citReceta) {
		this.citReceta = citReceta;
	}

	public String descEstado() {
		if (this.getCitEstado()==Cita.PENDIENTE) {
			return "Pendiente";
		}else {
			return "Cancelado";
		}
	}
	
	@Override
	public String toString() {
		return "Cita [citId=" + citId + ", citFecha=" + citFecha + ", citEstado=" + citEstado + ", medicomedid="
				+ medicomedid + ", pacientepacid=" + pacientepacid + "]";
	}


    
}
