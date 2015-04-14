package ec.com.vipsoft.ce.backend.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import ec.com.vipsoft.ce.utils.UtilClaveAcceso;

@Stateless
public class NotificadorCorreo {

//	public void notificarCorreo(Properties propiedadesConnection, String claveAcceso,String numeroAutorizacion){
//		
//	}

	 @Resource(name="jdbc/abinadi")
	 private DataSource ds;

	@Inject
	 private UtilClaveAcceso utilClaveAcceso;
//	@Schedule(dayOfMonth="*",hour="*",minute="*",second="0",year="*",month="*")
//	public void enviarNotificacionCorreo(){
//		MultiPartEmail email = new MultiPartEmail();
//		email.setHostName("smtp.googlemail.com");
//		email.setSmtpPort(465);
//		email.setAuthenticator(new DefaultAuthenticator("christian.valverde", "chrisvvdana"));
//		email.setSSLOnConnect(true);
//		try {
//			email.setFrom("christian.valverde@gmail.com");
//			email.setSubject("TestMail");
//			email.setMsg("This is a test mail ... :-)");
//			email.addTo("christian.valverde@gmail.com");
//			EmailAttachment attachment = new EmailAttachment();
//			  attachment.setPath("/home/chrisvv/Downloads/0804201507179173947700120020020000000120000000610.pdf");
//			  attachment.setDisposition(EmailAttachment.ATTACHMENT);
//			  attachment.setDescription("doc pdf");
//			  attachment.setName("John");
//			  email.attach(attachment);
//			email.send();
//		} catch (EmailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
	private static String HOST = "smtp.gmail.com";
    private static String USER = "christian.valverde@gmail.com";
    private static String PASSWORD = "chrisvvdana";
    private static String PORT = "465";
    private static String FROM = "christian.valverde@gmail.com";
    private static String TO = "christian.valverde@gmail.com";
 
    private static String STARTTLS = "true";
    private static String AUTH = "true";
    private static String DEBUG = "true";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static String SUBJECT = "Testing JavaMail API";
    private static String TEXT = "This is a test message from my java application. Just ignore it";
  //  @Schedule(dayOfMonth="*",hour="*",minute="*",second="0",year="*",month="*")
    public static synchronized void send() {
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
 
            //Obtain the default mail session
            Session session = Session.getInstance(props, null);
            session.setDebug(true);
 
            //Construct the mail message
            MimeMessage message = new MimeMessage(session);
            
            message.setSubject(SUBJECT);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(RecipientType.TO, new InternetAddress(TO));
            
            MimeBodyPart textPart=new MimeBodyPart();
//            textPart.setText("Adjuntamos archivo xml representando su comprobante electrónico emitido a su favor. También puede entrar al portal http://comprobantes.rocarsystem.com y usar su identificación (RUC/Cédula) como crendenciales");
//            
//            
            Multipart multipart=new MimeMultipart("mixed");
            multipart.addBodyPart(textPart);
            MimeBodyPart attachmentPart=new MimeBodyPart();
            attachmentPart.attachFile("/home/chrisvv/Downloads/0804201507179173947700120020020000000120000000610.pdf");
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
//            message.saveChanges();
            //message.saveChanges();
 
            //Use Transport to deliver the message
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
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
