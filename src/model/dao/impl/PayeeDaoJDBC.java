package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PayeeDao;
import model.entities.Payee;

public class PayeeDaoJDBC implements PayeeDao {

	private Connection conn;

	public PayeeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Payee payee) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"INSERT INTO payee "
					+ "(Name) "
					+ "VALUES "
					+ "(?)", 
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, payee.getName());
			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					payee.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error. No rows affected");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public Payee findById(int id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT "
					+ "* "
					+ "FROM payee "
					+ "WHERE "
					+ "Id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Payee payee = new Payee();
				payee.setId(rs.getInt("Id"));
				payee.setName(rs.getString("Name"));
				return payee;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}

	@Override
	public List<Payee> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT "
					+ "* "
					+ "FROM payee "
					+ "ORDER BY "
					+ "Name");
			rs = pst.executeQuery();
			List<Payee> list = new ArrayList<>();
			while (rs.next()) {
				Payee payee = new Payee();
				payee.setId(rs.getInt("Id"));
				payee.setName(rs.getString("Name"));
				list.add(payee);
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
	public void update(Payee payee) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"UPDATE payee SET "
					+ "Name = ? "
					+ "WHERE "
					+ "Id = ?");
			pst.setString(1, payee.getName());
			pst.setInt(2, payee.getId());
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

	@Override
	public void deleteById(int id) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"DELETE FROM payee "
					+ "WHERE "
					+ "Id = ?");
			pst.setInt(1, id);
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
}
