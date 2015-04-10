package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



/**
 * Entity implementation class for Entity: Establecimiento
 *
 */
@Entity
public class Establecimiento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String direccion;
	private String placaVehiculo;
	private String identificacionTransportista;
	private String razonSocialTransportista;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<PuntoVenta> pos;
	
	@OneToOne
	protected Entidad entidad;

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}



	public void anadirPos(int c) {
		int posicion=pos.size();
		
		for (int i = 1; i <= c; i++) {
			PuntoVenta pto = new PuntoVenta();
			pto.setCodigoPuntoVenta(String.valueOf(posicion+i));
			pos.add(pto);
		}
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<PuntoVenta> getPos() {
		return pos;
	}

	public void setPos(List<PuntoVenta> pos) {
		this.pos = pos;
	}

	public Establecimiento() {
		super();
		
		pos = new ArrayList<PuntoVenta>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public PuntoVenta getPuntoEmision(String codigo){
		PuntoVenta retorno=null;
		 
		for(PuntoVenta p:pos){
			if(p.getCodigoPuntoVenta().equalsIgnoreCase(codigo)){				
				retorno=p;
				break;
			}
		}
	
		return retorno;
	}
	
	
	public PuntoVenta getPuntosEmision() {
		PuntoVenta retorno=null;
		 
		for(PuntoVenta p:pos){
			if(p.isDefaultPuntoVentaElectronico()){				
				retorno=p;
				break;
			}
		}
	
		return retorno;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getIdentificacionTransportista() {
		return identificacionTransportista;
	}

	public void setIdentificacionTransportista(String identificacionTransportista) {
		this.identificacionTransportista = identificacionTransportista;
	}

	public String getRazonSocialTransportista() {
		return razonSocialTransportista;
	}

	public void setRazonSocialTransportista(String razonSocialTransportista) {
		this.razonSocialTransportista = razonSocialTransportista;
	}

}
