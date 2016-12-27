<?php

//echo $init->getModelesPath();

include $init->getModelesPath()."fonctions_connexion.php";


$connect = new modeles\fonctions_connexion();
    $err = "";
    if(isset($_POST['login'])){
        if(isset($_POST['password'])){
            $err = $connect->connecte($_POST['login'],$_POST['password']);
        } else {
            $err = "Veuillez saisir un mot de passe";
        }
    }

include $init->getViewsPath()."connexionView.php";

