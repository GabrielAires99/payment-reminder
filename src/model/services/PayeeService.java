package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PayeeDao;
import model.entities.Payee;

public class PayeeService {

	private PayeeDao dao = DaoFactory.createPayeeDao();

	public void insert(Payee payee) {
		dao.insert(payee);
	}

	public Payee findById(int id) {
		return dao.findById(id);
	}

	public List<Payee> findAll() {
		return dao.findAll();
	}

	public void update(Payee payee) {
		dao.update(payee);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
