package com.banque.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe parente de tous les services.
 */
abstract class AbstractService {
	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Constructeur de l'objet.
	 */
	protected AbstractService() {
		super();
	}
}
