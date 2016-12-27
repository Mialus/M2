<?php

/**
 * Created by PhpStorm.
 * User: Pierre
 * Date: 13/12/2016
 * Time: 02:17
 */
class Header
{
    function creerEntete($nom){
        echo '<!DOCTYPE html><html><head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title> Kiestla - '.$nom.'</title>
        <link rel="stylesheet" href="../CroissantShow/lib/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../CroissantShow/lib/bootstrap/css/bootstrap.min.css"/>
        </head><body>';
    }
}