<?php
/**
 * Created by PhpStorm.
 * User: cedric
 * Date: 10/12/2016
 * Time: 17:33
 */

include_once $init->getModelesPath()."modeles_admin.php";

$admin = new \modeles\modeles_admin();

$err = "";
if(isset($_POST['libelle'])){
    echo $admin->ajouterLibelle($_POST['libelle']);
} else {
    if(isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['identidiant']))
    {
        echo $admin->ajouterPersonne($_POST['nom'], $_POST['prenom'], $_POST['identidiant'], $_POST['optradio']);
    }
}

include_once $init->getViewsPath()."adminView.php";