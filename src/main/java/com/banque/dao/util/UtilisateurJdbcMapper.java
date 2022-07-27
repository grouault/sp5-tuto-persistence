package com.banque.dao.util;

import com.banque.dao.impl.UtilisateurDAO;
import com.banque.entity.ESex;
import com.banque.entity.IUtilisateurEntity;
import com.banque.entity.impl.UtilisateurEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

public class UtilisateurJdbcMapper implements RowMapper {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        IUtilisateurEntity result = new UtilisateurEntity();
        result.setId(Integer.valueOf(rs.getInt("id")));
        result.setNom(rs.getString("nom"));
        result.setPrenom(rs.getString("prenom"));
        result.setLogin(rs.getString("login"));
        result.setPassword(rs.getString("password"));
        // Gestion de l'enumeration
        byte sex = rs.getByte("sex");
        switch (sex) {
            case 0:
                result.setSex(ESex.HOMME);
                break;
            case 1:
                result.setSex(ESex.FEMME);
                break;
            default:
                UtilisateurJdbcMapper.LOG.warn("Sex a une valeur surprenante (" + sex + ")");
                result.setSex(ESex.AUTRE);
                break;
        }
        result.setDerniereConnection(rs.getTimestamp("derniereConnection"));
        result.setAdresse(rs.getString("adresse"));
        int cp = rs.getInt("codePostal");
        if (rs.wasNull()) {
            result.setCodePostal(null);
        } else {
            result.setCodePostal(Integer.valueOf(cp));
        }
        result.setTelephone(rs.getString("telephone"));
        result.setDateDeNaissance(rs.getDate("dateDeNaissance"));
        return result;
    }

}
