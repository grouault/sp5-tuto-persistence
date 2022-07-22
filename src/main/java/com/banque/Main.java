package com.banque;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.banque.entity.ICompteEntity;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;
import com.banque.service.impl.AuthentificationService;
import com.banque.service.impl.CompteService;
import com.banque.service.impl.OperationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Exemple.
 */
public final class Main {
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Constructeur.
	 */
	private Main() {
		super();
		Main.LOG.error("Ne pas utiliser le constructeur");
	}

	/**
	 * Exemple de fonctionnement.
	 *
	 * @param args
	 *            ne sert a rien
	 */
	public static void main(String[] args) {
		Main.LOG.info("-- Debut -- ");

		ClassPathXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("spring/*-context.xml");

			// recuperation d'un utilisateur via Spring
			IAuthentificationService serviceAuth =  context.getBean(IAuthentificationService.class);
			IUtilisateurEntity utilisateur = serviceAuth.authentifier("dj", "dj");
			Main.LOG.info("Bonjour {} {}", utilisateur.getNom(), utilisateur.getPrenom());

			// liste de ses comptes
			ICompteService serviceCpt = context.getBean(ICompteService.class);
			List<ICompteEntity> listeCpts = serviceCpt.selectAll(utilisateur.getId().intValue());
			Main.LOG.info("Vous avez {} comptes", String.valueOf(listeCpts.size()));

			// On prend deux id de comptes pour faire un virement
			Integer[] deuxId = new Integer[2];
			int id = 0;
			Iterator<ICompteEntity> iter = listeCpts.iterator();
			while (iter.hasNext() && id < deuxId.length) {
				ICompteEntity compteEntity = iter.next();
				deuxId[id] = compteEntity.getId();
				id++;
			}

			// realisation du virement
			IOperationService serviceOp = context.getBean(IOperationService.class);
			serviceOp.faireVirement(utilisateur.getId().intValue(), deuxId[0].intValue(), deuxId[1].intValue(), 5D);
			Main.LOG.info("Votre virement s'est bien effectue");

		} catch (Exception e) {
			Main.LOG.fatal("Erreur", e);
			System.exit(-1);
		} finally {
			if (context != null) {
				context.close();
			}
		}
		Main.LOG.info("-- Fin -- ");
		System.exit(0);
	}

}
