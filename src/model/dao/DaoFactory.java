package model.dao;

import db.DB;
import model.dao.impl.BillDaoJDBC;

public class DaoFactory {

	public static BillDao createBillDao() {
		return new BillDaoJDBC(DB.getConnection());
	}
}
