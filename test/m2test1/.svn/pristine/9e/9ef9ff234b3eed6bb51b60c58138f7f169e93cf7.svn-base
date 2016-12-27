<?php

include $init->getLib()."fonctionConnexion.php";
$err = "";

if(!isset($_POST['co'])) {
    if(isset($_POST['id'])){
        if(isset($_POST['mdp'])) {
            $err = $connect->connecte($_POST['id'], $_POST['mdp']);

        }else{
		    echo("identifiant ou mot de passe non valide");
        }

    }
		
}

include $init->getViewsPath()."connexionView.php";
?>
