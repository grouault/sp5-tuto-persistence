## release notes

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



