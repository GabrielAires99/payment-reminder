package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.BillDao;
import model.entities.Bill;
import model.entities.Payee;
import model.entities.Payer;
import model.entities.enums.Frequency;

public class BillDaoJDBC implements BillDao {

	private Connection conn;

	public BillDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Bill> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT " 
					+ "bill.*, " 
					+ "payer.Name as PayerName, payer.Email as PayerEmail, "
					+ "payee.Name as PayeeName " 
					+ "FROM bill " 
					+ "INNER JOIN payer " 
					+ "ON bill.PayerId = payer.Id "
					+ "INNER JOIN payee " + "ON bill.PayeeId = payee.Id " + "ORDER BY DueDate");
			rs = pst.executeQuery();
			List<Bill> list = new ArrayList<>();
			Map<Integer, Payee> payeeMap = new HashMap<>();
			Map<Integer, Payer> payerMap = new HashMap<>();
			while (rs.next()) {
				int payeeId = rs.getInt("PayeeId");
				Payee payee = payeeMap.get(payeeId);
				int payerId = rs.getInt("PayerId");
				Payer payer = payerMap.get(payerId);
				if (payee == null) {
					payee = instantiatePayee(rs);
					payeeMap.put(payeeId, payee);
				}
				if (payer == null) {
					payer = instantiatePayer(rs);
					payerMap.put(payerId, payer);
				}
				Bill bill = instantiateBill(rs, payer, payee);
				list.add(bill);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}

	@Override
	public void update(Bill bill) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"UPDATE bill SET "
					+ "Description = ?, DueDate = ?, Frequency = ?, PayerId = ?, PayeeId = ? "
					+ "WHERE "
					+ "Id = ?");
			pst.setString(1, bill.getDescription());
			pst.setDate(2, new Date(bill.getDueDate().getTime()));
			pst.setString(3, String.valueOf(bill.getFrequency()));
			pst.setInt(4, bill.getPayer().getId());
			pst.setInt(5, bill.getPayee().getId());
			pst.setInt(6, bill.getId());
			int rowsAffected = pst.executeUpdate();
			if (rowsAffected < 1) {
				throw new DbException("Unexpected error. No rows affected");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
	}
	
	private Payee instantiatePayee(ResultSet rs) throws SQLException {
		Payee payee = new Payee();
		payee.setId(rs.getInt("PayeeId"));
		payee.setName(rs.getString("PayeeName"));
		return payee;
	}

	private Payer instantiatePayer(ResultSet rs) throws SQLException {
		Payer payer = new Payer();
		payer.setId(rs.getInt("PayerId"));
		payer.setName(rs.getString("PayerName"));
		payer.setEmail(rs.getString("PayerEmail"));
		return payer;
	}

	private Bill instantiateBill(ResultSet rs, Payer payer, Payee payee) throws SQLException {
		Bill bill = new Bill();
		bill.setId(rs.getInt("Id"));
		bill.setDescription(rs.getString("Description"));
		bill.setDueDate(rs.getDate("DueDate"));
		bill.setFrequency(Frequency.valueOf(rs.getString("Frequency")));
		bill.setPayer(payer);
		bill.setPayee(payee);
		return bill;
	}
}
