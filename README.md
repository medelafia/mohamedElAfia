📘 BookShop Deployment – Server Configuration
👤 Informations du groupe

- Utilisateur Linux créé : mohamedElAfia
- Dossier de travail : /home/mohamedElAfia/bookshop
- Repository Docker Hub : mohamedelafia/bookshop:latest
- Emplacement du fichier docker-compose : /home/mohamedElAfiaa/docker-compose.yaml

🔹 1. Connexion initiale au serveur

Se connecter avec l’utilisateur administrateur fourni :
- ssh admin@37.27.214.35

🔹 2. Création de votre utilisateur personnel

Remplacer votreuser par le nom choisi (mohamedElAfia) :

- sudo useradd -m -s /bin/bash mohamedElAfia
- sudo passwd mohamedElAfia   # Définir un mot de passe
- sudo usermod -aG sudo mohamedElAfia

✅ À partir de maintenant, vous allez travailler avec votre utilisateur mohamedElAfia et non plus avec admin.

Se connecter avec votre nouvel utilisateur :

- su - mohamedElAfia

🔹 3. Création du dossier de travail

- mkdir -p /home/mohamedElAfia/bookshop
- cd /home/mohamedElAfia/bookshop

🔹 4. Placement du fichier docker-compose.yaml

Le fichier docker-compose.yaml est déjà disponible sur le serveur dans :

/home/mohamedElAfiaa/docker-compose.yaml
