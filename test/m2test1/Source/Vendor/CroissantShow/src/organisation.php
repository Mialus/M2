<?php
session_start();
include "../lib/spdo.php";

if(isset($_SESSION['login'])){
	if($_SESSION['admin']==1){
		echo ("<a href='ValidationInscription.php'>Valider les nouveaux inscrits</a>");
	}
}
echo("<a href='Deconnexion.php'>Déconnexion</a>");
$err = SPDO::getInstance()->afficheEvent();

?>
