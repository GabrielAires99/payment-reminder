package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import email.Email;
import model.entities.Bill;
import model.services.BillService;
import model.services.EmailService;
import model.services.GmailService;
import util.Utils;

public class Program {

	private static int DAYS_GAP;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {

		System.out.println("Running...");
		try {
			loadConstantProperties();
			EmailService es = new GmailService(Email.getSender());
			BillService bs = new BillService();
			List<Bill> list = bs.findAll();
			List<Bill> today = list.stream().filter(x -> Utils.isToday(x.getDueDate()) == true)
					.collect(Collectors.toList());
			List<Bill> next = list.stream().filter(x -> Utils.isNext(x.getDueDate(), DAYS_GAP) == true)
					.collect(Collectors.toList());
			if (today.size() > 0) {
				today.forEach(x -> es.sendMessage(
						x.getPayer().getEmail(), 
						"LEMBRETE PAGAMENTO",
						"Pagar " + x.getDescription() + " para " + x.getPayee().getName() + " hoje!"));
				today.forEach(Bill::updateDueDate);
				today.forEach(x -> bs.update(x));
			}
			if (next.size() > 0) {
				next.forEach(x -> es.sendMessage(
						x.getPayer().getEmail(), 
						"LEMBRETE PAGAMENTO",
						x.getDescription() + " está chegando! Vencimento: " + sdf.format(x.getDueDate())));
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Finished");
	}

	private static void loadConstantProperties() {
		try (FileInputStream fis = new FileInputStream("program.properties")) {
			Properties props = new Properties();
			props.load(fis);
			DAYS_GAP = Integer.parseInt(props.getProperty("daysgap"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
