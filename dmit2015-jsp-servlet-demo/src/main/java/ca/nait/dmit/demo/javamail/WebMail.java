package ca.nait.dmit.demo.javamail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class WebMail {

	private Properties mailProperties;
	
	
	public WebMail() throws IOException {
		super();
		// Create a new Properties object
		mailProperties = new Properties();
		// Load data into the Properties object
		mailProperties.load(getClass().getResourceAsStream("/javamail.properties"));
		
	}
	
	public void send(String mailToEmail, String mailToName, String subject, String message) throws Exception {
		// 1) Start a mail session with the "Session.getInstance()" method.
		Session currentSession = Session.getInstance(mailProperties);
		// 2) Create a new "Message" object
		MimeMessage currentMessage = new MimeMessage(currentSession);
		// 3) Set the message's From: address
		Address fromAddress = new InternetAddress(mailProperties.getProperty("mail.smtp.from"), mailProperties.getProperty("mail.from.name"));
		currentMessage.setFrom(fromAddress);
		// 4) Set the message's To: address
		Address toAddress = new InternetAddress(mailToEmail, mailToName);
		currentMessage.setRecipient(Message.RecipientType.TO, toAddress);
		// 5) Set the message's subject
		currentMessage.setSubject(subject);
		// 6) Set the content of the message
		currentMessage.setText(message);						
		// 7) Get a "Transport" from the session
		try (Transport currentTransport = currentSession.getTransport()) {
			// 8) Connect the transport to a named host using a email and a password
			currentTransport.connect(
					mailProperties.getProperty("mail.smtp.from"), 
					mailProperties.getProperty("mail.password"));
			// 9) Send the message to all recipients over the transport
			currentTransport.sendMessage(currentMessage, currentMessage.getAllRecipients());				
		}			
	}

	public static void main(String[] args) {
		try {
			WebMail app = new WebMail();
			
			String mailToEmail = JOptionPane.showInputDialog("What is your email address?");
			String mailToName = JOptionPane.showInputDialog("What is your name?");
			String subject = JOptionPane.showInputDialog("What is the subject for the email?");
			String message = JOptionPane.showInputDialog("What is the message for the email?");
			
			long startTime = System.currentTimeMillis();
			app.send(mailToEmail, mailToName, subject, message);
			long sendDuration = System.currentTimeMillis() - startTime;
			JOptionPane.showMessageDialog(null, "Send mail was successful in " + sendDuration + " milliseconds");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Send mail was not successful.");
			e.printStackTrace();
		}
	}

}
