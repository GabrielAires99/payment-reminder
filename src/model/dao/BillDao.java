package model.dao;

import java.util.List;

import model.entities.Bill;

public interface BillDao {

	void insert(Bill bill);

	Bill findById(int id);

	List<Bill> findAll();
	
	void update(Bill bill);

	void deleteById(int id);
}
