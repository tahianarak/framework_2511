///Pour utiliser ce framework,il faut:
    .importer l'annotation Controller se trouvant dans le package mg.ituprom16.annotation
    .annoter chaque controller de cette annotation 
    .declarer le servelet FrontController se trouvant dans le package mg.ituprom16 avec pour mapping '/'
    .specifier la source de package des controllers dans votre xml dans le servelet FrontController
    .la source package est un parametre de init-param avec le nom "package-source" 
    .chaque methode doit etre annotee avec Get avec pour valeur l'url associe