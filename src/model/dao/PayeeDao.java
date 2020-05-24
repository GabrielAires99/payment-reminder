package model.dao;

import java.util.List;

import model.entities.Payee;

public interface PayeeDao {

	void insert(Payee payee);

	Payee findById(int id);

	List<Payee> findAll();
	
	void update(Payee payee);

	void deleteById(int id);
}
