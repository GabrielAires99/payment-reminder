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
import model.dao.PayerDao;
import model.entities.Payer;

public class PayerDaoJDBC implements PayerDao {

	private Connection conn;

	public PayerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Payer payer) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"INSERT INTO payer "
					+ "(Name, Email) "
					+ "VALUES "
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, payer.getName());
			pst.setString(2, payer.getEmail());
			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					payer.setId(id);
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
	public Payer findById(int id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT "
					+ "* "
					+ "FROM payer "
					+ "WHERE "
					+ "Id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Payer payer = new Payer();
				payer.setId(rs.getInt("Id"));
				payer.setName(rs.getString("Name"));
				payer.setEmail(rs.getString("Email"));
				return payer;
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
	public List<Payer> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT "
					+ "* "
					+ "FROM payer "
					+ "ORDER BY "
					+ "Name");
			rs = pst.executeQuery();
			List<Payer> list = new ArrayList<>();
			while (rs.next()) {
				Payer payer = new Payer();
				payer.setId(rs.getInt("Id"));
				payer.setName(rs.getString("Name"));
				payer.setEmail(rs.getString("Email"));
				list.add(payer);
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
	public void update(Payer payer) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"UPDATE payer SET "
					+ "Name = ?, Email = ? "
					+ "WHERE "
					+ "Id = ?");
			pst.setString(1, payer.getName());
			pst.setString(2, payer.getEmail());
			pst.setInt(3, payer.getId());
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
					"DELETE FROM payer "
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
