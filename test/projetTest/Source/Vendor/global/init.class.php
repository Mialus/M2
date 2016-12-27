<?php

/**
 * Created by PhpStorm.
 * User: cedric
 * Date: 10/12/2016
 * Time: 13:10
 */
class init{

    function __construct(){

        session_start();

        include_once $this->getLibPath()."spdo.class.php";
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


    function userIsConnected(){
        return isset($_SESSION['id_utilisateur']);
    }


    function getControllersPath(){
        return "../Vendor/controllers/";
    }

    function getModelesPath(){
        return "../Vendor/modeles/";
    }

    function getViewsPath(){
        return "../Vendor/views/";
    }

    function getGlobalPath(){
        return "../Vendor/global/";
    }

    function getLibPath(){
        return "../Vendor/lib/";
    }

    function executerInsertion($reqSQL)
    {
        try {
            $bd = SPDO::getInstance();
            $pbd = $bd->prepare($reqSQL);
            $res = $pbd->execute();
            if ($res == true) {
                return 0;
            } else {
                return "Erreur lors de l'insertion";
            }
        } catch (PDOException $e) {
            return $e->getMessage();
        }
    }

    function executerSelect($reqSQL)
    {
        try {
            $bd = SPDO::getInstance();
            $pbd = $bd->prepare($reqSQL);
            $res = $pbd->execute();
            if ($res == true) {
                return $pbd;
            } else {
                return "Erreur lors du select";
            }
        } catch (PDOException $e) {
            return $e->getMessage();
        }
    }


}