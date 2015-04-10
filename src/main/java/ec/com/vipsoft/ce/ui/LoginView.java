package ec.com.vipsoft.ce.ui;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;

@CDIView("login")
public class LoginView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3823202018387876166L;
	private TextField usuario;
	private PasswordField password;
	private Button botonLogIn;
	private CheckBox rememberMe;
	@Inject
	private RegistradorUsuario registradorUsuario;
	
	@Inject 
	private UserInfo userInfo;

	public LoginView() {
		super();
		setMargin(true);
		setSpacing(true);
		usuario = new TextField();
		usuario.setInputPrompt("email@domain.com");
		password = new PasswordField();
		botonLogIn = new Button("LOG IN");
		rememberMe = new CheckBox("registrame");
		addComponent(usuario);
		addComponent(password);
		addComponent(rememberMe);
		addComponent(botonLogIn);
		// Sha256Hash hasher=new Sha256Hash();

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// if(SecurityUtils.getSubject().isAuthenticated()){
		// SecurityUtils.getSubject().logout();
		// }

	}

	@PostConstruct
	public void postconstruir() {

		botonLogIn.addClickListener(event -> {
			System.out.println("se ha hecho click");
			if (rememberMe.getValue()) {
				registradorUsuario.registrarUsuario(usuario.getValue(),	password.getValue(), "", "");
			} else {
				UsernamePasswordToken uptoken=new UsernamePasswordToken();
				uptoken.setPassword(password.getValue().toCharArray());
				uptoken.setUsername(usuario.getValue());
				Subject currentUser=SecurityUtils.getSubject();
				try{
					currentUser.login(uptoken);
					Map<String, String> mapa = registradorUsuario.llenarUserInfo(usuario.getValue());
					if(mapa.size()>0){
						userInfo.setRucEmisor(mapa.get("rucEmisor"));
						userInfo.setCodigoEstablecimiento(mapa.get("codigoEstablecimiento"));
					//	userInfo.setIdEstablecimiento(Integer.valueOf(mapa.get("idEstablecimiento")));
						userInfo.setPuntoEmision(mapa.get("puntoEmision"));
						userInfo.setRazonSocialEmisor(mapa.get("razonSocialEmisor"));
					//	userInfo.setIdPuntoEmision(Integer.valueOf(mapa.get("idPuntoEmision")));	
					}
					
					
					
					if(currentUser.hasRole("operador")){
						//amarrar el login con las variables de usuario
						
						UI.getCurrent().getNavigator().navigateTo("menu");
					}
//					}else{
//						if(currentUser.hasRole("usuario")){
//							//no se amarra a ninguna variable ... por lo menos por ahora
//							UI.getCurrent().getNavigator().navigateTo("portal");
//						}
//					}
				}catch(AuthenticationException e){
					Notification.show("error", e.getMessage(),Type.ERROR_MESSAGE);
				}
			}
		});
	}
}
