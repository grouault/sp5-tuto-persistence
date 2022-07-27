package com.banque.dao.impl;

import com.banque.dao.IDAO;
import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * DAO abstrait.
 *
 * @param <T>
 *            Un type d'entite
 */
@Repository

public abstract class AbstractDAO<T extends IEntity> implements IDAO<T> {

	private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Constructeur de l'objet.
	 */
	protected AbstractDAO() {
		super();
	}

	/**
	 * Retourne le nom de la table.
	 *
	 * @return le nom de la table.
	 */
	protected abstract String getTableName();

	/**
	 * Retourne la clef primaire de la table.
	 *
	 * @return la clef primaire de la table.
	 */
	protected String getPkName() {
		return "id";
	}

	/**
	 * Retourne la liste des colonnes de la table
	 *
	 * @return la liste des colonnes de la table
	 */
	protected abstract String getAllColumnNames();

	/**
	 * A la responsabilite de creer un statement qui servira pour l'insertion.
	 *
	 * @param pUneEntite
	 *            une entite a inserer
	 * @param connexion
	 *            une connexion
	 * @return un statement adapte a l'insertion
	 * @throws SQLException
	 *             si un probleme survient
	 */
	protected abstract PreparedStatement buildStatementForInsert(T pUneEntite, Connection connexion)
			throws SQLException;

	/**
	 * A la responsabilite de creer un statement qui servira pour la mise a
	 * jour.
	 *
	 * @param pUneEntite
	 *            une entite a mettre a jour
	 * @param connexion
	 *            une connexion
	 * @return un statement adapte a la mise a jour
	 * @throws SQLException
	 *             si un probleme survient
	 */
	protected abstract PreparedStatement buildStatementForUpdate(T pUneEntite, Connection connexion)
			throws SQLException;

	/**
	 * permet de récupérer le rowMapper adequat
	 * @return
	 */
	protected abstract RowMapper<T> getMapper();

	@Override
	public T insert(final T pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		AbstractDAO.LOG.debug("Insert {}", pUneEntite.getClass());
		try {
			KeyHolder kh = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					return buildStatementForInsert(pUneEntite, connection);
				}
			}, kh);
			pUneEntite.setId(Integer.valueOf(kh.getKey().intValue()));
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}
		return pUneEntite;
	}

	@Override
	public T update(final T pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		AbstractDAO.LOG.debug("update {}", pUneEntite.getClass());
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		try {
			this.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					return buildStatementForUpdate(pUneEntite, connection);
				}
			});
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}
		return pUneEntite;
	}

	@Override
	public boolean delete(T pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return false;
		}
		AbstractDAO.LOG.debug("delete {}", pUneEntite.getClass());
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		try {
			String sql = "delete from " + this.getTableName() + " where " + this.getPkName() + "=?;";
			return 1 == jdbcTemplate.update(sql, pUneEntite.getId().intValue());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}
	}

	@Override
	public T select(int pUneClef) throws ExceptionDao {
		T result = null;
		AbstractDAO.LOG.debug("select sur {} avec id={}", this.getClass(), String.valueOf(pUneClef));
		String sql = "select " + this.getAllColumnNames() + " from " + this.getTableName() + " where "
				+ this.getPkName() + "=?;";
		try {
			 result = this.getJdbcTemplate().queryForObject(sql, this.getMapper(), pUneClef);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (Exception ex) {
			throw new ExceptionDao(ex);
		}
		return result;
	}

	@Override
	public List<T> selectAll(String pAWhere, String pAnOrderBy) throws ExceptionDao {
		List<T> result = new ArrayList<T>();
		AbstractDAO.LOG.debug("selectAll sur {} avec where={} prderBy={}", this.getClass(), pAWhere, pAnOrderBy);
		try {
			StringBuilder request = new StringBuilder();
			request.append("select ").append(this.getAllColumnNames()).append(" from ");
			request.append(this.getTableName());
			if (pAWhere != null) {
				request.append(" where ");
				request.append(pAWhere);
			}
			if (pAnOrderBy != null) {
				request.append(" order by ");
				request.append(pAnOrderBy);
			}
			request.append(';');
			AbstractDAO.LOG.debug("selectAll sur {} - requete={}", this.getClass(), request.toString());

			String sql = request.toString();
			result = this.getJdbcTemplate().query(sql, this.getMapper());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}
		return result;
	}

	/**
	 * Place les elements dans la requete.
	 *
	 * @param ps
	 *            la requete
	 * @param gaps
	 *            les elements
	 * @throws SQLException
	 *             si un des elements ne rentre pas
	 */
	protected static final void setPrepareStatement(PreparedStatement ps, List<?> gaps) throws SQLException {
		Iterator<?> iter = gaps.iterator();
		int id = 0;
		while (iter.hasNext()) {
			id++;
			Object lE = iter.next();
			if (lE == null) {
				continue;
			}
			if (lE instanceof String) {
				ps.setString(id, (String) lE);
			} else if (lE instanceof java.sql.Date) {
				ps.setDate(id, (java.sql.Date) lE);
			} else if (lE instanceof java.util.Date) {
				ps.setDate(id, new java.sql.Date(((java.util.Date) lE).getTime()));
			} else if (lE instanceof Timestamp) {
				ps.setTimestamp(id, (Timestamp) lE);
			} else if (lE instanceof Integer) {
				ps.setInt(id, ((Integer) lE).intValue());
			} else if (lE instanceof Double) {
				ps.setDouble(id, ((Double) lE).doubleValue());
			} else {
				throw new SQLException("Type invalid '" + lE.getClass().getSimpleName() + "'");
			}

		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}