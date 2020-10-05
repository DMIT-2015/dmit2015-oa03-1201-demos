package ca.nait.dmit.demo.properties;

import java.io.IOException;
import java.util.Properties;

public class JavaPropertiesDemo {

	public static void main(String[] args) {
		JavaPropertiesDemo app = new JavaPropertiesDemo();
		app.displayProperties();
	}

	public void displayProperties() {
		// Read the applicaton.properties file and display values for the properties
		// mail.to and mail.to.name
		Properties appProps = new Properties();
		try {
			appProps.load(getClass().getResourceAsStream("/application.properties"));
			String mailTo = appProps.getProperty("mail.to");
			String mailToName = appProps.getProperty("mail.to.name");
			String message = String.format("Email: %s, Name: %s", mailTo, mailToName);
			System.out.println(message);
			
			if (appProps.getProperty("user2015") != null) {
				System.out.println("The password for user2015 is " + appProps.getProperty("user2015"));
			}
			if (appProps.getProperty("admin2015") == null) {
				System.out.println("No such user admin2015");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
