package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PayerDao;
import model.entities.Payer;

public class PayerService {

	private PayerDao dao = DaoFactory.createPayerDao();

	public void insert(Payer payer) {
		dao.insert(payer);
	}

	public Payer findById(int id) {
		return dao.findById(id);
	}

	public List<Payer> findAll() {
		return dao.findAll();
	}

	public void update(Payer payer) {
		dao.update(payer);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
