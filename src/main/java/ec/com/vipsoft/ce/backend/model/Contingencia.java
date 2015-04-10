package ec.com.vipsoft.ce.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import ec.com.vipsoft.erp.abinadi.dominio.Entidad;

/**
 * representa los  numeros de autorizacion generados por el sri y dados como autorizaciones de contingencia.
 * se los debe usar cuando el sri no responda
 * @author chrisvv
 *
 */
@Entity
@Table(name = "contingencia")
public class Contingencia implements Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 5249485844508681769L;
@Id   
   @Column(name = "id", updatable = false, nullable = false,length=37)
   private String id;
   @Version
   @Column(name = "version")
   private int version;
   private boolean usado;
   @Temporal(TemporalType.TIMESTAMP)	
   private Date fechaUso;
   @ManyToOne
   private Entidad entidad;
   public boolean isUsado() {
	return usado;
}

public void setUsado(boolean usado) {
	this.usado = usado;
}

public Date getFechaUso() {
	return fechaUso;
}

public void setFechaUso(Date fechaUso) {
	this.fechaUso = fechaUso;
}

public String getId()
   {
      return this.id;
   }

   public void setId(final String id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof Contingencia))
      {
         return false;
      }
      Contingencia other = (Contingencia) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

public Entidad getEntidad() {
	return entidad;
}

public void setEntidad(Entidad entidad) {
	this.entidad = entidad;
}


}