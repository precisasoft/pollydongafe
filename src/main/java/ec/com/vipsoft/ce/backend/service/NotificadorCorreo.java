package ec.com.vipsoft.ce.backend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;

@Stateless
public class NotificadorCorreo {

	

	 public NotificadorCorreo() {
		super();
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean esEmailValido(final String hex) {

		if ((hex != null)) {
			matcher = pattern.matcher(hex);
			return matcher.matches();
		} else {
			return false;
		}

	}

	@Resource(name="jdbc/abinadi")
	 private DataSource dataSource;
	 @PersistenceContext
	 private EntityManager em;
	 
	 private static final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Inject
	 private UtilClaveAcceso utilClaveAcceso;
	private Pattern pattern;
	private Matcher matcher;
	
	private static String HOST = "smtp.gmail.com";
    private static String USER = "facturacion@rocarsystem.com";
    private static String PASSWORD = "rocarsystem2013";
    private static String PORT = "465";
    private static String FROM = "facturacion@rocarsystem.com";
    private static String TO = "christian.valverde@gmail.com";
 
    private static String STARTTLS = "true";
    private static String AUTH = "true";
    private static String DEBUG = "true";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static String SUBJECT = "Comprobante electrónico nuevo";
    private static String TEXT = "Ud. ha recibido un comprobante electrónico nuevo . Puede visualizarlo en http://comprobantes.rocarsystem.com  use su identificación (RUC/cédula) tanto como usuario y contraseña";
	
	
//	private static String HOST = "smtp.gmail.com";
//    private static String USER = "tesoreriahgiide@gmail.com";
//    private static String PASSWORD = "hospital1234";
//    private static String PORT = "465";
//    private static String FROM = "tesoreriahgiide@gmail.com";
//    private static String TO = "christian.valverde@gmail.com";
// 
//    private static String STARTTLS = "true";
//    private static String AUTH = "true";
//    private static String DEBUG = "true";
//    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
//    private static String SUBJECT = "Comprobante electrónico nuevo";
//    private static String TEXT = "Ud. ha recibido un comprobante electrónico nuevo . Puede visualizarlo en http://hosptialmilitariide.comprobanteselectronicos.ec  use su identificación (RUC/cédula) tanto como usuario y contraseña";
 
    @Schedule(dayOfMonth="*",hour="*",minute="*",second="5,35",year="*",month="*")
    public  void send() {
        //Use Properties object to set environment properties
        Properties props = new Properties();
 
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", USER);//
 
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);
 
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
 
        try {
        	GregorianCalendar ahora=new GregorianCalendar();
    		ahora.add(GregorianCalendar.HOUR_OF_DAY, -24);
    		Connection 	conexion = dataSource.getConnection();
    			PreparedStatement pst=conexion.prepareStatement("select * from vistaXenviarCorreo where fecha_a >=?");
    			pst.setDate(1, new java.sql.Date(ahora.getTimeInMillis()));
    			//pst.setDate(2, new java.sql.Date(new Date().getTime()));
    			ResultSet rst=pst.executeQuery();
    			String servidorLei="0000000";
    			Session sesion=null;    				
    			while(rst.next()){
    				String correoDestino=rst.getString("correoelectronico");
    				
    				if(esEmailValido(correoDestino)){
    					Session session = Session.getInstance(props, null);
        	            session.setDebug(true);
        	 
        	            //Construct the mail message
        	            MimeMessage message = new MimeMessage(session);
        	            StringBuilder sbsubject=new StringBuilder(SUBJECT);
        	            String claveAcceso=rst.getString("claveacceso");
        	            String tipoDocumento = utilClaveAcceso.obtenerTipoDocumento(claveAcceso);
        	            String _tipoDocumento=null;
        	            if(tipoDocumento.equalsIgnoreCase("01")){
        	            	_tipoDocumento=" Factura";    	           
        	            }
        	            if(tipoDocumento.equalsIgnoreCase("04")){
        	            	_tipoDocumento=" Nota de crédito";
        	            }
        	            if(tipoDocumento.equalsIgnoreCase("05")){
        	            	_tipoDocumento=" Nota de dédito";
        	            }
        	            if(tipoDocumento.equalsIgnoreCase("06")){
        	            	_tipoDocumento=" Guía de remisión";
        	            }
        	            if(tipoDocumento.equalsIgnoreCase("07")){
        	            	_tipoDocumento=" Comprobante de renteción";
        	            }    	            
        	            StringBuilder sbNumeroDocumento=new StringBuilder(utilClaveAcceso.obtenerCodigoEstablecimiento(claveAcceso));
        	            sbNumeroDocumento.append("-");
        	            sbNumeroDocumento.append(utilClaveAcceso.obtenerCodigoPuntoEmision(claveAcceso));
        	            sbNumeroDocumento.append("-");
        	            sbNumeroDocumento.append(utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
        	            
        	            sbsubject.append(_tipoDocumento);
        	            sbsubject.append(" ").append(sbNumeroDocumento.toString());
        	            
        	            message.setSubject(sbsubject.toString());
        	            message.setFrom(new InternetAddress(FROM));
        	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino)); 
        	            StringBuilder sbtext=new StringBuilder(TEXT);
        	            sbtext.append(" "+_tipoDocumento).append(" N° "+sbNumeroDocumento.toString()+" con clave de acceso "+claveAcceso);
        	            String numeroAutorizacion=rst.getString("autorizacion");
        	            sbtext.append(" con código de autorización "+numeroAutorizacion);
        	            message.setText(sbtext.toString());
//        	            message.saveChanges();
        	            //message.saveChanges();
        	 
        	            //Use Transport to deliver the message
        	            Transport transport = session.getTransport("smtp");
        	            transport.connect(HOST, USER, PASSWORD);
        	            transport.sendMessage(message, message.getAllRecipients());
        	            transport.close();   	
        	            ComprobanteElectronico ce=em.find(ComprobanteElectronico.class, rst.getLong("id"));
        	            ce.setNotificadoBeneficiario(true);
    				}
    		           //Obtain the default mail session
    	            
    			}
    			conexion.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    
    
    
    public String limpiarDireccion(String direccion){
    	String sincomillas=direccion.replace("'", "");
    	
    	
    	return sincomillas;
    }
	 
//	 @Schedule(dayOfMonth="*",hour="*",minute="*",second="0",year="*",month="*")
//	public void enviarNotificacionCE(){
//			MimeMessage message=new MimeMessage(mailSession);
//			 try {
//				message.setFrom(new InternetAddress("christian.valverde@gmail.com"));
//				   message.setRecipient(Message.RecipientType.TO, new InternetAddress("christian.valverde@gmail.com"));
//				   message.setSubject("Ud. ha recibido un nuevo comprobante electrónico");
//		            message.setSentDate(new Date());
//		            
//		           
//		            MimeBodyPart textPart=new MimeBodyPart();
//		            textPart.setText("Adjuntamos archivo xml representando su comprobante electrónico emitido a su favor. También puede entrar al portal http://comprobantes.rocarsystem.com y usar su identificación (RUC/Cédula) como crendenciales");
//		            
//		            
//		            Multipart multipart=new MimeMultipart();
//		            multipart.addBodyPart(textPart);
//		            MimeBodyPart attachmentPart=new MimeBodyPart();
//		            attachmentPart.attachFile("/home/chrisvv/Downloads/0804201507179173947700120020020000000120000000610.pdf");
//		            multipart.addBodyPart(attachmentPart);
//		            message.setContent(multipart);
//		            message.saveChanges();
//		            Transport.send(message,message.getAllRecipients());
//			} catch (AddressException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (MessagingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	 
//         
//	
//	}
//	
//	
//	@Schedule(dayOfMonth="*",hour="*",minute="*",second="15,35,55",year="*",month="*")
//	public void enviarEmailtest(){
//		GregorianCalendar ahora=new GregorianCalendar();
//		ahora.add(GregorianCalendar.HOUR_OF_DAY, -24);
//		Connection conexion;
//		try {
//			conexion = ds.getConnection();
//			PreparedStatement pst=conexion.prepareStatement("select * from vistaXenviarCorreo where fecha_a >=?");
//			pst.setDate(1, new java.sql.Date(ahora.getTimeInMillis()));
//			//pst.setDate(2, new java.sql.Date(new Date().getTime()));
//			ResultSet rst=pst.executeQuery();
//			String servidorLei="0000000";
//			Session sesion=null;
//			Properties props=new Properties();			
//			while(rst.next()){
//				
//					props.put("mail.smtp.host",rst.getString("hostcorreo"));
//					props.put("mail.smtp.port","587");
//					props.put("mail.smtp.starttls.enable", "true");
//				//	props.put("mail.transport.protocol",rst.getString("transportecorreo"));
//					props.put("mail.smtp.auth","true"); 
//					//props.put("mail.smtp.user",rst.getString("corremoemisor")); 
//				//	props.put("password",rst.getString("passowrdcorreoemisor")); 
//					//props.put("mail.from",rst.getString("corremoemisor"));
//				//	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//				//	props.put("mail.smtp.socketFactory.fallback",false); 
//					sesion=Session.getDefaultInstance(props,new Authenticator() {
//						@Override
//						protected PasswordAuthentication getPasswordAuthentication(){
//							return new PasswordAuthentication("christian.valverde", "chrisvvdana");
//						}
//					});
//					sesion.setDebug(true);
//					MimeMessage message=new MimeMessage(sesion);
//					 try {
//						message.setFrom(new InternetAddress(rst.getString("corremoemisor")));
//						   message.setRecipient(Message.RecipientType.TO, new InternetAddress(rst.getString("correoelectronico")));
//						   message.setSubject("Ud. ha recibido un nuevo comprobante electrónico");
//				            message.setSentDate(new Date());
//				            
//				           
//				            MimeBodyPart textPart=new MimeBodyPart();
//				            textPart.setText("Adjuntamos archivo xml representando su comprobante electrónico emitido a su favor. También puede entrar al portal http://comprobantes.rocarsystem.com y usar su identificación (RUC/Cédula) como crendenciales");
//				            
//				            
//				            Multipart multipart=new MimeMultipart();
//				            multipart.addBodyPart(textPart);
//				            MimeBodyPart attachmentPart=new MimeBodyPart();
//				            attachmentPart.attachFile("/home/chrisvv/Downloads/0804201507179173947700120020020000000120000000610.pdf");
//				            multipart.addBodyPart(attachmentPart);
//				            message.setContent(multipart);
//				            message.saveChanges();
//				            Transport.send(message,message.getAllRecipients());
//					} catch (AddressException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (MessagingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			         
//			            
//				
//			}
//			conexion.close();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		  
//	}
}
