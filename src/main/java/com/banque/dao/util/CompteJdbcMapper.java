package com.banque.dao.util;

import com.banque.entity.ICompteEntity;
import com.banque.entity.impl.CompteEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CompteJdbcMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ICompteEntity result = new CompteEntity();
        result.setId(rs.getInt("id"));
        result.setLibelle(rs.getString("libelle"));
        result.setUtilisateurId(rs.getInt("utilisateurId"));
        double solde = rs.getDouble("solde");
        if (!rs.wasNull()) {
            result.setSolde(Double.valueOf(solde));
        } else {
            result.setSolde(null);
        }
        double vd = rs.getDouble("decouvert");
        // Le decouvert etait-il null ?
        boolean dnull = rs.wasNull();
        double vt = rs.getDouble("taux");
        // Le taux etait-il null ?
        boolean tnull = rs.wasNull();
        if (!dnull) {
            result.setDecouvert(Double.valueOf(vd));
        } else {
            result.setDecouvert(null);
        }
        if (!tnull) {
            result.setTaux(Double.valueOf(vt));
        } else {
            result.setTaux(null);
        }
        return result;
    }

}
