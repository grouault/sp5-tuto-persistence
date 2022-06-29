package com.banque;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.banque.entity.ICompteEntity;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;

/**
 * Exemple.
 */
// indique les répertoires ou rechercher les annotations.
public final class Main {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Exemple de fonctionnement.
     *
     * @param args
     *            ne sert a rien
     */
    public static void main(String[] args) throws Exception {
        Main.LOG.info("-- Debut -- ");

        // Permet de démarrer Spring.
        // Permet de charger Spring : recherche d'annotation sur la classe Main.
        // Spring va rechercher les annotations.
        // context = zone de mémoire avec ses propres valeurs spring <==> context
        ClassPathXmlApplicationContext context = null;
        try {

            context = new ClassPathXmlApplicationContext("spring/*-context.xml");
            System.out.println("OK ca marche GO!!");
            // On instancie le service authentification afin de recuperer un
            // utilisateur
            // ici j'ai été explicite, mon code ne peut pas évoluer
            // ==> trop dépendant de certaines classes.
            // IAuthentificationService serviceAuth = new AuthentificationService();

            // On instancie le service de gestion des comptes pour recuperer la
            // liste de ses comptes

            // On prend deux id de comptes pour faire un virement


            // On Effectue un virement entre deux comptes, via le service des
            // operations
            // IOperationService serviceOp = new OperationService();


        } catch (BeansException e) {
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
