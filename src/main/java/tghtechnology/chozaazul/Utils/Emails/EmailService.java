package tghtechnology.chozaazul.Utils.Emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	 @Autowired
	 private JavaMailSender javaMailSender;
	 
	 /**
	  * Envía un email utilizando la dirección de correo proporcionara en el archivo .properties
	  * @param email Email a mandar, formado por una clase que hereda de BaseEmail
	  * @throws MessagingException
	  */
	 public void enviarEmail(BaseEmail email) throws MessagingException {
		 
		 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		 
		 helper.setTo(email.getDestinatario());
		 helper.setSubject(email.getAsunto());
		 helper.setText(email.buildEmail(), true);
		 
		 //Adjuntando emails
		 email.getAdjuntos().entrySet().forEach(entry ->{
			 try {
				helper.addAttachment(entry.getKey(), entry.getValue());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		 });
		 
		 //System.out.println("Enviando a: " + email.getDestinatario());
		 
		 javaMailSender.send(mimeMessage);
		 
	 }
	
}
