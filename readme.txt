///Pour utiliser ce framework,il faut:
    .importer l'annotation Controller se trouvant dans le package mg.ituprom16.annotation
    .annoter chaque controller de cette annotation 
    .declarer le servelet FrontController se trouvant dans le package mg.ituprom16 avec pour mapping '/'
    .specifier la source de package des controllers dans votre xml dans le servelet FrontController
    .la source package est un parametre de init-param avec le nom "package-source" 
    .chaque methode doit etre annotee avec Get avec pour valeur l'url associe
    .le type de retour de la methode doit etre un String ou un ModelView
    .ModelView se trouve dans le paquet mg.ituprom16.affloader
    .pour en construire un il faut appeler son constructeur avec l'url associe a la package
    .pour envoyer des donnees, il faut utiliser la methode addObject(String,Object)
    .Pour envoyer des donnees vers un controller il faut:
        -Soit faire correspondre les inputs avec le nom des parametres de la fonction (les classes doivent etre compiler avec l'option parameters)
        -Soit utiliser l'annnotation Match pour specifier l input qui lui correspond
    