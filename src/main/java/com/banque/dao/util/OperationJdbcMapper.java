package com.banque.dao.util;

import com.banque.entity.IOperationEntity;
import com.banque.entity.impl.OperationEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class OperationJdbcMapper implements RowMapper<IOperationEntity> {

    @Override
    public IOperationEntity mapRow(ResultSet rs, int i) throws SQLException {

        IOperationEntity result = new OperationEntity();
        result.setId(rs.getInt("id"));
        result.setLibelle(rs.getString("libelle"));
        double montant = (rs.getDouble("montant"));
        if (!rs.wasNull()) {
            result.setMontant(Double.valueOf(montant));
        } else {
            result.setMontant(null);
        }
        result.setDate(rs.getTimestamp("date"));
        result.setCompteId(rs.getInt("compteId"));
        return result;
    }

}
