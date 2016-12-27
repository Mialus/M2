<?php

/**
 * Created by PhpStorm.
 * User: Pierre
 * Date: 13/12/2016
 * Time: 02:39
 */
class fonctionConnexion
{
    function connecte($id, $mdp)
    {
        try {
            $bd = SPDO::getInstance();
            $pbd = $bd->prepare("SELECT COUNT(login) as user, lastname, firstname, adherant, admin FROM USER WHERE login=".$id." and pwd=".$mdp."");;
            $pbd->bindValue(':login', $id);
            $pbd->bindValue(':pass', $mdp);
            $pbd->execute();
        } catch (PDOException $e) {
            echo $e->getMessage();
        }

        if ($pbd->rowCount() > 0) {
            while ($donnees = $pbd->fetch()) {
                $_SESSION['login'] = $id;
                $_SESSION['nom'] = $donnees['firstname'];
                $_SESSION['prenom'] = $donnees['lastname'];
                $_SESSION['adherant'] = $donnees['adherant'];
                $_SESSION['admin'] = $donnees['admin'];
                $_SESSION['id_utilisateur'] = $donnees['id_utilisateur'];
                $_SESSION['role'] = $donnees['role'];
            }
            echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
        } else {
            return "Utilisateur inconnu";
        }
    }


}