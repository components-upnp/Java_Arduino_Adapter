# Java_Arduino_Adapter
Composant qui adapte les composants basés sur Arduino.

<strong>Description : </strong>

Ce composant sert à pour objectif d'adapter les communications entre les composants Arduino et les autres composants UPnP.
Par exemple on pourra adapter le composant Boutons Arduino avec la visionneuse de diapositive.
Ce composant prend en entrée la commande envoyée par événement UPnP par un composant Arduino et l'encapsule dans un fichier
XML en y ajoutant son UDN avant de lui même le transmettre via UPnP.

C'est un composant logiciel, il ne présente pas d'interface graphique.

<strong>Lancement de l'application : </strong>

Pour lancer l'application, exécuter lanceur.bat ou lanceur.sh respectivement si sur Windows ou Linux.

<strong>Spécifications UPnP : </strong>

Ce composant présente deux services dont voici les descriptions :

  a) AdaptedCommandService :
  
    1) SetAdaptedCommand(String AdaptedCommand) : reçoit la commande Arduino adaptée et la transmet via UPnP dans un événement 
    AdaptedCommand_Event.
    
  b) ArduinoCommandService :
  
    1) SetArduinoCommand(String ArduinoCommand) : prend en entrée une commande Arduino à adapter et la transmet à l'application.
    
    
Voici le schéma représentant ce composant ainsi que ses services :

![alt tag](https://github.com/components-upnp/Java_Arduino_Adapter/blob/master/Arduino_Adapter.png)

<strong>Maintenance : </strong>

L'application se présente sous la forme d'un projet Maven.
Pour générer les exécutables lancer la phase install en choisissant le profil souhaité (Windows ou Linux).
