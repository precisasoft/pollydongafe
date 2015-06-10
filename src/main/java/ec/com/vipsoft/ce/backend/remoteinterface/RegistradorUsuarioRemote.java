package ec.com.vipsoft.ce.backend.remoteinterface;

import javax.ejb.Remote;

@Remote
public interface RegistradorUsuarioRemote {
	public boolean registrarUsuario(String userName, String password,String nombres, String apellidos);
}
