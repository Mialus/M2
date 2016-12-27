<?php

/**
 * Created by PhpStorm.
 * User: cedric
 * Date: 10/12/2016
 * Time: 13:28
 */
class header
{

    function CreateHtmlPage($pageName){
        echo '<!DOCTYPE html><html><head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title> Kiestla - '.$pageName.'</title>
        <link rel="stylesheet" href="../Vendor/lib/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../Vendor/lib/bootstrap/css/bootstrap.min.css"/>
        </head><body>';
    }


    function CreateMenu($role){
        echo '<div class="navbar navbar-default navbar-fixed-top">
                    <div class="container">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a asp-area="" asp-controller="Home" asp-action="Index" class="navbar-brand">Kiesla</a>
                        </div>
                        <div class="navbar-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li><a asp-area="" asp-controller="Home" asp-action="Index">Home</a></li>
                                <li><a asp-area="" asp-controller="Home" asp-action="About">About</a></li>
                                <li><a asp-area="" asp-controller="Home" asp-action="Contact">Contact</a></li>
                            </ul>
                            <ul class="nav navbar-nav navbar-right">
                              <li><a href="index.php?page=deconnexion"><span class="glyphicon glyphicon-log-out"></span> Deconnexion</a></li>
                            </ul>
                        </div>
                    </div>
                </div>';
    }


}