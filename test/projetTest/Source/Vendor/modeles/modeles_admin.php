<?php

namespace modeles;
use SPDO;
/**
 * Description of controleur_admin
 *
 * @author clement
 */
class modeles_admin
{

    public function ajouterLibelle($nom)
    {
        $bd = SPDO::getInstance();
        $pbd = $bd->prepare("INSERT INTO `MODULE`(`libelle`) VALUES (:nom)");
        $pbd->bindValue(':nom', $nom);
        if ($pbd->execute()) {
            return "Ajout ok";
        } else {
            return "Erreur lors de l'ajout : " . $pbd->errorInfo();
        }
    }

    public function ajouterPersonne($nom, $prenom, $id, $role)
    {

        $log = genererLog($nom, $prenom);

        $bd = SPDO::getInstance();
        $pbd = $bd->prepare("INSERT INTO `UTILISATEUR`(`nom`, `prenom`, `id_utilisateur`, `login`, `mdp`, `role`)
            VALUES (:nom,:prenom, :id,:log, :mdp, :role)");
        $pbd->bindValue(':nom', $nom);
        $pbd->bindValue(':prenom', $prenom);
        $pbd->bindValue(':id', \intval($id));
        $pbd->bindValue(':log', $log);
        $pbd->bindValue(':mdp', $log);
        $pbd->bindValue(':role', $role);
        if ($pbd->execute()) {
            return "Ajout ok";
        } else {
            return "Erreur lors de l'ajout : " . implode(',', $pbd->errorInfo());
        }
    }


}

function genererLog($nom, $prenom)
{
    $length = strlen($nom);
    $log = substr($prenom, 0, 1) . substr($nom, 0, ($length > 4 ? 5 : $length));
    $inc = 0;
    $bd = SPDO::getInstance();
    do {
        $inc++;
        $pbd = $bd->prepare("SELECT * FROM `UTILISATEUR` WHERE `login`= :loginc");
        $pbd->bindValue(':loginc', $log . $inc);
        $pbd->execute();
    } while ($pbd->rowCount() > 0);


    return $log . $inc;
}

