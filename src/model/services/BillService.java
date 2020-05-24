package model.services;

import java.util.List;

import model.dao.BillDao;
import model.dao.DaoFactory;
import model.entities.Bill;

public class BillService {

	private BillDao dao = DaoFactory.createBillDao();

	public void insert(Bill bill) {
		dao.insert(bill);
	}

	public Bill findById(int id) {
		return dao.findById(id);
	}

	public List<Bill> findAll() {
		return dao.findAll();
	}

	public void update(Bill bill) {
		dao.update(bill);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
