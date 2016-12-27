<?php

    include_once "../Vendor/global/init.class.php";

$init = new init();

    include_once $init->getGlobalPath()."header.class.php";

$header = new header();

ob_start();

if($init->userIsConnected()) {
    //echo "<script type='text/javascript'>document.location.replace('index.php?connexion');</script>";
    //header('Location : connexion.php');

    $header->CreateHtmlPage("index");

    $header->CreateMenu(1);

    echo "<br><br><br><br>";


    if (!empty($_GET['page']) && is_file($init->getControllersPath().$_GET['page'].'.php')) {
        include_once $init->getControllersPath() . $_GET['page'] . '.php';
    } elseif ($_SESSION['role'] == 0){
        include_once $init->getControllersPath().'admin.php';
    }
    include_once $init->getGlobalPath()."footer.php";

}else{
    include_once $init->getControllersPath()."connexion.php";
}

$contenu = ob_get_clean();
echo $contenu;



