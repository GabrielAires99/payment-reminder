package model.services;

public interface EmailService {
	
	public void sendMessage(String to, String subject, String content);
}
