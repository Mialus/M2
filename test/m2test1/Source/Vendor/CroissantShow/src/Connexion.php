<?php
	session_start();
	include(dirname(__DIR__).'/lib/spdo.php');

	if(isset($_POST['login']) && isset($_POST['password'])) {
		if(SPDO::getInstance()->userConnection($_POST['login'],$_POST['password'])) {
			header('Location: organisation.php');
		} else {
			echo "Utilisateur inconnu";
		}
	}	
	
	include(dirname(__DIR__).'/view/connexionView.php');
?>
