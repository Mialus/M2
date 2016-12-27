

<div>
    <p><h2>Ajout d'un module</h2></p>
    <div class="container">
            <div class="form-group row">
                <form class="form-signin" method="POST">
                    <input type="text" name="libelle" id="inputLibelleModule" class="form-control" placeholder="Libellé" required autofocus>
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Enregistrer</button>
                </form><!-- /form -->
            </div><!-- /card-container -->
        </div><!-- /container -->
</div>
<div>
    <p><h2>Ajout d'une personne</h2></p>
    <div class="container">
            <div class="form-group row">
                <form class="form-signin" method="POST">
                    <input type="text" name="nom" id="inputLibelleModule" class="form-control" placeholder="Nom" required autofocus>
                    <input type="text" name="prenom" id="inputLibelleModule" class="form-control" placeholder="Prenom" required autofocus>
                    <input type="text" name="identidiant" id="inputLibelleModule" class="form-control" placeholder="Numero d'identifiant" required autofocus>
                    <label class="radio-inline">
                    <input type="radio" name="optradio" value="1" checked>Enseignant
                    </label>
                     <label class="radio-inline">
                         <input type="radio" name="optradio" value="2" >Etudiant
                    </label>
                     <label class="radio-inline">
                    <input type="radio" name="optradio" value="3">Personnel administratif
</label>
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Enregistrer</button>
                </form><!-- /form -->
            </div><!-- /card-container -->
        </div><!-- /container -->
</div>