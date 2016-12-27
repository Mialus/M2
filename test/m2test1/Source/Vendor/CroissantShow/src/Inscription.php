<?php
	include(dirname(__DIR__).'/lib/spdo.php');

	if(isset($_POST['prenom']) && isset($_POST['nom']) && isset($_POST['mail']) && isset($_POST['pass']) && isset($_POST['pass_repeat'])) {
		if(SPDO::getInstance()->userInscription($_POST['mail'],$_POST['pass'],$_POST['nom'],$_POST['prenom'])) {
			header('Location: Connexion.php');
		} else {
			echo "Inscription échouée";
		}
	}
	
	include(dirname(__DIR__).'/view/inscriptionView.php');
?>
