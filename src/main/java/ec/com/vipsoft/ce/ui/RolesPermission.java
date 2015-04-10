package ec.com.vipsoft.ce.ui;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the roles_permissions database table.
 * 
 */
@Entity
@Table(name="roles_permissions")
@NamedQuery(name="RolesPermission.findAll", query="SELECT r FROM RolesPermission r")
public class RolesPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolesPermissionPK id;

	public RolesPermission() {
	}

	public RolesPermissionPK getId() {
		return this.id;
	}

	public void setId(RolesPermissionPK id) {
		this.id = id;
	}

}