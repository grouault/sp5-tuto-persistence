package com.banque.service.impl;

import com.banque.service.IOperationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ex.MauvaisMotdepasseException;
import com.banque.service.ex.UtilisateurInconnuException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test sur la classe IAuthentificationService.
 */
public class TestUtilisateurService {
	private static final Logger LOG = LogManager.getLogger();
	private static IAuthentificationService authentificationService;

	private static ClassPathXmlApplicationContext context = null;

	/**
	 * Initialisation du log. <br/>
	 * Appele au demarrage de tous les tests.
	 */
	@BeforeClass
	public static void initLog() {
		context = new ClassPathXmlApplicationContext("spring/*-context.xml");
		TestUtilisateurService.authentificationService = context.getBean(IAuthentificationService.class);
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
	public void testAuthentifierOk() {
		final String login = "df";
		final String pwd = "df";
		IUtilisateurEntity user = null;
		try {
			user = TestUtilisateurService.authentificationService.authentifier(login, pwd);
		} catch (Exception e) {
			TestUtilisateurService.LOG.error("Erreur", e);
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull("L'utilisateur ne doit pas etre null", user);
		Assert.assertEquals("Le login de l'utilisateur est " + login, user.getLogin(), login);
	}

	/**
	 * Test
	 *
	 * @throws Exception
	 *             UtilisateurInconnuException attendue
	 */
	@Test(expected = UtilisateurInconnuException.class)
	public void testAuthentifierKo1() throws Exception {
		final String login = "dfd";
		final String pwd = "df";
		TestUtilisateurService.authentificationService.authentifier(login, pwd);
	}

	/**
	 * Test
	 *
	 * @throws Exception
	 *             MauvaisMotdepasseException attendue
	 */
	@Test(expected = MauvaisMotdepasseException.class)
	public void testAuthentifierKo2() throws Exception {
		final String login = "df";
		final String pwd = "dfd";
		TestUtilisateurService.authentificationService.authentifier(login, pwd);
	}

}
