package com.banque.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.banque.dao.ICompteDAO;
import com.banque.dao.IOperationDAO;
import com.banque.dao.IUtilisateurDAO;
import com.banque.dao.impl.CompteDAO;
import com.banque.dao.impl.OperationDAO;
import com.banque.dao.impl.UtilisateurDAO;

/*
 * on peut créer autant de configuration que l'on souhaite.
 */
@Configuration
@PropertySource("classpath:database.properties")
public class DaoConfig {

	@Autowired
	private Environment env;
	
	class Conf {
		public String driver;
		public String url;
		public String login;
		public String pwd;
	}	
	
	private Conf config() {
		Conf cf = new Conf();
		cf.driver = env.getProperty("bdd.driver");
		cf.url = env.getProperty("bdd.url");
		cf.login = env.getProperty("bdd.login");
		cf.pwd =  env.getProperty("bdd.pwd");
		return cf;
	}
	
	@Bean
	public ICompteDAO getCompteDAO() {
		Conf cf = config();
		ICompteDAO cdao = new CompteDAO(cf.url, cf.driver, cf.login, cf.pwd) ;
		return cdao;
	}

	/**
	 * autre config. pour compte DAO
	 * @return
	 */
	/*
	@Bean
	public ICompteDAO secureCompteDAO() {
		ICompteDAO cdao = new CompteDAO();
		// ???
		return cdao;
	}
	*/

	@Bean
	public IOperationDAO getOperationDAO() {
		Conf cf = config();
		IOperationDAO oDao = new OperationDAO(cf.url, cf.driver, cf.login, cf.pwd);
		return oDao;
	}

	@Bean
	public IUtilisateurDAO getUtilisateurDAO() {
		Conf cf = config();
		IUtilisateurDAO uDao = new UtilisateurDAO(cf.url, cf.driver, cf.login, cf.pwd);
		return uDao;
	}
	
}
