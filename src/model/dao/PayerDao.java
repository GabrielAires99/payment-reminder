package model.dao;

import java.util.List;

import model.entities.Payer;

public interface PayerDao {

	void insert(Payer payer);
	
	Payer findById(int id);

	List<Payer> findAll();

	void update(Payer payer);

	void deleteById(int id);
}
