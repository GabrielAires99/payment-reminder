package model.dao;

import db.DB;
import model.dao.impl.BillDaoJDBC;
import model.dao.impl.PayeeDaoJDBC;
import model.dao.impl.PayerDaoJDBC;

public class DaoFactory {

	public static BillDao createBillDao() {
		return new BillDaoJDBC(DB.getConnection());
	}

	public static PayerDao createPayerDao() {
		return new PayerDaoJDBC(DB.getConnection());
	}

	public static PayeeDao createPayeeDao() {
		return new PayeeDaoJDBC(DB.getConnection());
	}
}
