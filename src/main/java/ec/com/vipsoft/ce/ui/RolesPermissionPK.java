package ec.com.vipsoft.ce.ui;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the roles_permissions database table.
 * 
 */
@Embeddable
public class RolesPermissionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="role_name")
	private String roleName;

	private String roleper;

	public RolesPermissionPK() {
	}
	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleper() {
		return this.roleper;
	}
	public void setRoleper(String roleper) {
		this.roleper = roleper;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RolesPermissionPK)) {
			return false;
		}
		RolesPermissionPK castOther = (RolesPermissionPK)other;
		return 
			this.roleName.equals(castOther.roleName)
			&& this.roleper.equals(castOther.roleper);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleName.hashCode();
		hash = hash * prime + this.roleper.hashCode();
		
		return hash;
	}
}