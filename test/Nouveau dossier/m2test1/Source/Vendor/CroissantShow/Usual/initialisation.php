<?php

/**
 * Created by PhpStorm.
 * User: Pierre
 * Date: 13/12/2016
 * Time: 02:08
 */
class initialisation
{
    function _construct(){
        include_once $this->getLib()."spdo.php";
        session_start();

        // Désactivation des guillemets magiques
        ini_set('magic_quotes_runtime', 0);
        //set_magic_quotes_runtime(0);

        if (1 == get_magic_quotes_gpc())
        {
            function remove_magic_quotes_gpc(&$value) {

                $value = stripslashes($value);
            }
            array_walk_recursive($_GET, 'remove_magic_quotes_gpc');
            array_walk_recursive($_POST, 'remove_magic_quotes_gpc');
            array_walk_recursive($_COOKIE, 'remove_magic_quotes_gpc');
        }
    }

    function getLib(){
        return "../CroissantShow/lib/";
    }

    function userIsConnected(){
        return isset($_SESSION['id_utilisateur']);
    }

    function getSRCPath(){
        return "../CroissantShow/src/";
    }

    function getModelesPath(){
        return "../CroissantShow/modele/";
    }

    function getViewsPath(){
        return "../CroissantShow/view/";
    }

    function getUsualPath(){
        return "../CroissantShow/Usual/";
    }

    function insertionSQL($reqSQL)
    {
        try {
            $bd = SPDO::getInstance();
            $pbd = $bd->prepare($reqSQL);
            $res = $pbd->execute();
            if ($res == true) {
                return 0;
            } else {
                return "Insertion SQL Error";
            }
        } catch (PDOException $e) {
            return $e->getMessage();
        }
    }

    function selectSQL($reqSQL)
    {
        try {
            $bd = SPDO::getInstance();
            $pbd = $bd->prepare($reqSQL);
            $res = $pbd->execute();
            if ($res == true) {
                return $pbd;
            } else {
                return "Select SQL Error";
            }
        } catch (PDOException $e) {
            return $e->getMessage();
        }
    }
}