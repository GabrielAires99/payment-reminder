package model.dao;

import java.util.List;

import model.entities.Bill;

public interface BillDao {

	List<Bill> findAll();
	
	void update(Bill bill);
}
