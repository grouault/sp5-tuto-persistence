## release notes

### exo10: Jdbc et Spring - JdbcTemplate
* création d'une dataSource apache dbcp
* ajout dépendance POM:
  * apache-dbcp
  * spring-jdbc
  * spring-test
* Transformation des clases Java
  * mise en place de JdbcTemplate
  * suppresion de la gestion des transactions.
  * Attention : elles sont donc pour l'instant désactivées
    ==>  il ne faut JAMAIS demander la Connexion au JdbcTemplate, 
    nous verrons pourquoi lors de la réinsertion des transactions dans le code

* Liste des tansformations :
  * AbstractDAO
    * suppression méthode getConnexion
    * suppression méthode handleTransaction
    * suppression de la Connection en paramètre de toutes les méthodes
    * suppression méthode fromResultSet
    * création des mappers (RowMapper)
      * implémentation dans les childrens
    * gestion de l'exception EmptyResultDataAccessException
    * méthode insert (utilisation d'un KeyHolder)

### exo9 : AOP
- configuration d'un Aspect : LogAspect
- ajout de dépendance AspectJ dans le pom.xml
- réactivation du fichier spring-context.xml
  - activation du scan des annotation
  - activation des aspects

### exo8 : annotation_v0_AnnotationConfigApplicationContext
- chargement de la config à partir de la classe Main.java
- utilisation de l'annotation @ComponentScan

### exo8 : annotation_v0_@propertySource
- chargement du properties dans la classe via l'annotation @propertySource
- injection des valeurs par annotation: @Value
- seule configuration xml : Scan des annotations

### exo8 : annotation_v0_xml_contextPropertyPlaceHolder
- chargement du properties en xml via un propertyPlaceHolder
- injection des valeurs par annotation: @Value

### exo8 : annotation_v0_propertiesFactoryBean
- mise en place des annotations
  - annotation: @Repository, @Service 
  - injection par les constructeurs : @Autowired
- database.properties : configuré en XML
  - aucun autre bean en XML
- scan configuré en XML

### 1-declaration-beans:
- Exo 7 : correspond à l'exo.7 finalisé.
- mise en place de la configuration avec des beans spring xml
- injection par configuration xml
- transformation : des news en injection Spring
    - dans les DAOs
    - dans les Services
- Configuration de la base de données en properties
    - PropertyFactoryBean

### 0-version-initiale: 
  - version initiale du projet
  - pas d'utilisation de Spring
  - Configuration de la base de donnéés dans 
    - IDAO (DB_URL, DB_LOGIN, DB_PWD)



