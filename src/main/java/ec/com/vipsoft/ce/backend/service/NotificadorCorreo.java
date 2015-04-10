package ec.com.vipsoft.ce.backend.service;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Stateless
public class NotificadorCorreo {

//	public void notificarCorreo(Properties propiedadesConnection, String claveAcceso,String numeroAutorizacion){
//		
//	}
	//@Schedule(dayOfMonth="*",hour="*",minute="*",second="15,35,55",year="*",month="*")
	public void enviarEmailtest(){
		  try {
	            
	            Properties props=new Properties();
	            props.setProperty("mail.smtp.host",  "smtp.gmail.com");
	            props.setProperty("mail.smtp.starttls.enable", "true");
	            props.setProperty("mail.smtp.port", "587");
	            props.setProperty("mail.smtp.auth", "true");
	            
	            
	            
	            Session session=Session.getDefaultInstance(props);
	            MimeMessage message=new MimeMessage(session);
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress("christian.valverde@gmail.com"));
	            
	            message.setSubject("prueba de envio");
	            message.setText("como estas esta es una prueba de envio");
	            
	            // Se compone el adjunto con la imagen
	            BodyPart adjunto = new MimeBodyPart();
	            adjunto.setDataHandler(
	                new DataHandler(new FileDataSource("/home/chrisvv/prueba.txt")));
	            adjunto.setFileName("prueba.txt");
	            
	            // Una MultiParte para agrupar texto e imagen.
	            MimeMultipart multiParte = new MimeMultipart();
	            
	            multiParte.addBodyPart(adjunto);
	            message.setContent(multiParte);
	            
	            Transport t=session.getTransport("smtp");
	            t.connect("christian.valverde@gmail.com", "chrisvvdana");
	            t.sendMessage(message, message.getAllRecipients());
	            t.close();
	            
	        } catch (Exception e) {
	            System.err.println(e.getMessage());
	        }
	}
}
