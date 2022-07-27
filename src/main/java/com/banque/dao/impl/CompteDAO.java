package com.banque.dao.impl;

import com.banque.dao.util.CompteJdbcMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.banque.dao.ICompteDAO;
import com.banque.entity.ICompteEntity;
import com.banque.entity.impl.CompteEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Gestion des comptes.
 */
@Component
public class CompteDAO extends AbstractDAO<ICompteEntity> implements ICompteDAO {
	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Constructeur de l'objet.
	 */
	public CompteDAO() {
		super();
	}

	@Override
	protected String getTableName() {
		return "compte";
	}

	@Override
	protected String getAllColumnNames() {
		return "id,libelle,solde,decouvert,taux,utilisateurId";
	}

	@Override
	protected PreparedStatement buildStatementForInsert(ICompteEntity pUneEntite, Connection connexion)
			throws SQLException {
		String request = "insert into " + this.getTableName()
				+ " (libelle, solde, decouvert, taux, utilisateurId) values (?,?,?,?,?);";
		PreparedStatement ps = connexion.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, pUneEntite.getLibelle());
		ps.setDouble(2, pUneEntite.getSolde().doubleValue());
		if (pUneEntite.getDecouvert() != null) {
			ps.setDouble(3, pUneEntite.getDecouvert().doubleValue());
		} else {
			ps.setNull(3, Types.DOUBLE);
		}
		if (pUneEntite.getTaux() != null) {
			ps.setDouble(4, pUneEntite.getTaux().doubleValue());
		} else {
			ps.setNull(4, Types.DOUBLE);
		}
		ps.setInt(5, pUneEntite.getUtilisateurId().intValue());
		return ps;
	}

	@Override
	protected PreparedStatement buildStatementForUpdate(ICompteEntity pUneEntite, Connection connexion)
			throws SQLException {
		String request = "update " + this.getTableName()
				+ " set libelle=?, solde=?, decouvert=?, taux=?, utilisateurId=? where " + this.getPkName() + "=?;";
		PreparedStatement ps = connexion.prepareStatement(request);
		ps.setString(1, pUneEntite.getLibelle());
		ps.setDouble(2, pUneEntite.getSolde().doubleValue());
		if (pUneEntite.getDecouvert() != null) {
			ps.setDouble(3, pUneEntite.getDecouvert().doubleValue());
		} else {
			ps.setNull(3, Types.DOUBLE);
		}
		if (pUneEntite.getTaux() != null) {
			ps.setDouble(4, pUneEntite.getTaux().doubleValue());
		} else {
			ps.setNull(4, Types.DOUBLE);
		}
		ps.setInt(5, pUneEntite.getUtilisateurId().intValue());
		ps.setInt(6, pUneEntite.getId().intValue());
		return ps;
	}

	@Override
	protected RowMapper<ICompteEntity> getMapper() {
		return new CompteJdbcMapper();
	}

}