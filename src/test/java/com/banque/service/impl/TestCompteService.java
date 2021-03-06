package com.banque.service.impl;

import com.banque.dao.ICompteDAO;
import com.banque.dao.impl.TestCompteDAO;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.banque.entity.ICompteEntity;
import com.banque.service.ICompteService;
import com.banque.service.ex.AucunDroitException;
import com.banque.service.ex.EntityIntrouvableException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test sur la classe ICompteService.
 */
public class TestCompteService {
	private static final Logger LOG = LogManager.getLogger();

	private static ICompteService compteService;

	private static ClassPathXmlApplicationContext context = null;

	/**
	 * Initialisation du log. <br/>
	 * Appele au demarrage de tous les tests.
	 */
	@BeforeClass
	public static void initLog() {
		context = new ClassPathXmlApplicationContext("spring/*-context.xml");
		TestCompteService.compteService = context.getBean(ICompteService.class);
	}

	@AfterClass
	public static void endTest() {
		LOG.info("endTest");
		if (context != null) {
			context.close();
		}
	}

	/**
	 * Test
	 */
	@Test
	public void selectOk() {
		final int unUtilisateurId = 1;
		final int unCompteId = 12;
		ICompteEntity cpt = null;
		try {
			cpt = TestCompteService.compteService.select(unUtilisateurId, unCompteId);
		} catch (Exception e) {
			TestCompteService.LOG.error("Erreur", e);
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull("Le compte ne doit pas etre null", cpt);
		Assert.assertEquals("L'id du compte doit etre " + unCompteId, cpt.getId().intValue(), unCompteId);
	}

	/**
	 * Test
	 *
	 * @throws Exception
	 *             AucunDroitException attendue
	 */
	@Test(expected = AucunDroitException.class)
	public void selectKo1() throws Exception {
		// il n'y a pas d'utilisateur 100
		final int unUtilisateurId = 100;
		// il y a un compte 12
		final int unCompteId = 12;
		TestCompteService.compteService.select(unUtilisateurId, unCompteId);
	}

	/**
	 * Test
	 *
	 * @throws Exception
	 *             EntityIntrouvableException attendue
	 */
	@Test(expected = EntityIntrouvableException.class)
	public void selectKo2() throws Exception {
		// il y a un utilisateur 1
		final int unUtilisateurId = 1;
		// il n'y a pas de compte 12000
		final int unCompteId = 12000;
		TestCompteService.compteService.select(unUtilisateurId, unCompteId);
	}

	/**
	 * Test
	 */
	@Test
	public void selectAllOk() {
		// il y a un utilisateur 1
		final int unUtilisateurId = 1;
		List<ICompteEntity> liste = null;
		try {
			liste = TestCompteService.compteService.selectAll(unUtilisateurId);
		} catch (Exception e) {
			TestCompteService.LOG.error("Erreur", e);
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull("La liste ne doit pas etre null", liste);
		Assert.assertFalse("La liste ne doit pas etre vide", liste.isEmpty());
		for (ICompteEntity compteEntity : liste) {
			Assert.assertNotNull("Le compte ne doit pas etre null", compteEntity);
			Assert.assertEquals("Le compte doit avoir un user id = " + unUtilisateurId,
					compteEntity.getUtilisateurId().intValue(), unUtilisateurId);
		}
	}

}
