package email;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import email.entities.Sender;

public class Email {

	private static Sender sender;

	public static Sender getSender() {
		if (sender == null) {
			try {
				Properties props = loadProperties();
				String address = props.getProperty("address");
				String password = props.getProperty("password");
				sender = new Sender(address, password);
			} catch (Exception e) {
				throw new EmailException(e.getMessage());
			}
		}
		return sender;
	}

	private static Properties loadProperties() {
		try (FileInputStream fis = new FileInputStream("email.properties")) {
			Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (IOException e) {
			throw new EmailException(e.getMessage());
		}
	}
}
