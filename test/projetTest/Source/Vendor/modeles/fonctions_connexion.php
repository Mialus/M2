<?php

class fonctions_connexion
{
    function connecte($login, $pass)
    {
        try {
            $bd = SPDO::getInstance();
            $pbd = $bd->prepare("SELECT id_utilisateur, role FROM UTILISATEUR WHERE login = :login AND mdp = :pass AND role <> 2");
            $pbd->bindValue(':login', $login);
            $pbd->bindValue(':pass', $pass);
            $pbd->execute();
        } catch (PDOException $e) {
            echo $e->getMessage();
        }

        if ($pbd->rowCount() > 0) {
            while ($donnees = $pbd->fetch()) {
                $_SESSION['id_utilisateur'] = $donnees['id_utilisateur'];
                $_SESSION['role'] = $donnees['role'];
            }
            echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
        } else {
            return "Utilisateur inconnu";
        }
    }
}

?>