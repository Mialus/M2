<!DOCTYPE html>
<html>
<head>
    <title>Connexion à Qui est la ?</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../Vendor/lib/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="../Vendor/lib/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../Vendor/css/connexion.css"/>
</head>
<body>


<div class="container">
    <div class="card card-container">
        <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
        <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
        <p id="profile-name" class="profile-name-card"></p>
        <p><?= $err?></p>
        <form class="form-signin" method="POST">
            <input type="text" name="login" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
            <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Mot de passe" required>
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Se connecter</button>
        </form><!-- /form -->
        <a href="#" class="forgot-password">
            Mot de passe oublié ?
        </a>
    </div><!-- /card-container -->
</div><!-- /container -->


<script src="../Vendor/lib/bootstrap/js/bootstrap.js"></script>
<script src="../Vendor/lib/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>