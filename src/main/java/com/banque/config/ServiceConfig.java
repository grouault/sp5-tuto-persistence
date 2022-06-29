package com.banque.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.banque.dao.ICompteDAO;
import com.banque.dao.IOperationDAO;
import com.banque.dao.IUtilisateurDAO;
import com.banque.service.IAuthentificationService;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;
import com.banque.service.impl.AuthentificationService;
import com.banque.service.impl.CompteService;
import com.banque.service.impl.OperationService;

@Configuration
public class ServiceConfig {

	@Bean(value="authentificationService")
	@Autowired
	public IAuthentificationService getAuthentificationService(IUtilisateurDAO utilisateurDAO) {
		IAuthentificationService authService = new AuthentificationService(utilisateurDAO);
		return authService;
	}
	
	@Bean(value="compteService")
	@Autowired
	public ICompteService getCompteService(ICompteDAO compteDAO) {
		ICompteService serviceCompte = new CompteService(compteDAO);
		return serviceCompte;
	}
	
	@Bean(value="operationService")
	@Autowired
	public IOperationService getOperationService(IOperationDAO operationDAO, ICompteDAO compteDAO) {
		IOperationService serviceOperation = new OperationService(operationDAO, compteDAO);
		return serviceOperation;
	}
	
}
