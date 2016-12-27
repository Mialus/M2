<?php

/**
 * Created by PhpStorm.
 * User: cedric
 * Date: 10/12/2016
 * Time: 16:01
 */

$_SESSION = array();

session_destroy();

echo "<script type='text/javascript'>document.location.replace('./index.php');</script>";