package com.banque.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.banque.dao.IUtilisateurDAO;
import com.banque.dao.ex.ExceptionDao;
import com.banque.dao.impl.UtilisateurDAO;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ex.ErreurTechniqueException;
import com.banque.service.ex.FonctionnelleException;
import com.banque.service.ex.MauvaisMotdepasseException;
import com.banque.service.ex.UtilisateurInconnuException;

/**
 * Gestion de l'authentification.
 */
public class AuthentificationService extends AbstractService implements IAuthentificationService {
	private static final Logger LOG = LogManager.getLogger();
	private IUtilisateurDAO utilisateurDAO;

	/**
	 * Constructeur de l'objet.
	 */
	public AuthentificationService() {
		super();
		this.utilisateurDAO = new UtilisateurDAO();
	}

	/**
	 * Recupere la propriete <i>utilisateurDAO</i>.
	 *
	 * @return the utilisateurDAO la valeur de la propriete.
	 */
	protected IUtilisateurDAO getUtilisateurDAO() {
		return this.utilisateurDAO;
	}

	@Override
	public IUtilisateurEntity authentifier(String pLogin, String pPassword)
			throws FonctionnelleException, ErreurTechniqueException {
		AuthentificationService.LOG.debug("authentifier {} Xxxx", pLogin);
		if (pLogin == null || pLogin.trim().length() == 0) {
			throw new IllegalArgumentException("login");
		}
		if (pPassword == null || pPassword.trim().length() == 0) {
			throw new IllegalArgumentException("password");
		}
		IUtilisateurEntity resultat = null;
		try {
			resultat = this.getUtilisateurDAO().selectLogin(pLogin, null);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
		if (resultat == null) {
			throw new UtilisateurInconnuException();
		}
		if (!pPassword.equals(resultat.getPassword())) {
			throw new MauvaisMotdepasseException();
		}
		AuthentificationService.LOG.debug("authentifier Resultat = {} ", resultat);
		return resultat;
	}
}
