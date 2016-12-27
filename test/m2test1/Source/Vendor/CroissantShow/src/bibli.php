<?php

function echo_html_head($titre, $css)
{
	ob_start();
	echo
		'<!doctype html>',
		'<html>',
			'<head>',
				'<title>', $titre, '</title>', 
				'<meta charset="UTF-8">',
				'<link rel="stylesheet" type="text/css" href="', $css, '">',	
			'</head>',
			'<body>';
}

function echo_html_end()
{
	echo
			'</body>',
		'</html>';
	ob_end_flush();
}


function Deconnexion(){
	session_write_close();
	echo("<script type='text/javascript'>document.location.replace('Connexion.php');</script>");
}



?>
