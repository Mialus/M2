#ifndef WRITE_H_INCLUDED
#define WRITE_H_INCLUDED

#include <vector>

namespace cvsXlsToFet {

  class write {
  public:
    write(std::vector<std::string> matiere, std::vector<std::string> salle, std::vector<std::vector<std::string>> etudiant, std::vector<std::string> filiere);
    ~write();

    //ecris dans le fet
    int writeInFet();
    
    //cre des groupes d'etudiant en fonction de leur contrainte 
    void creationGoupeEtudiant();

  protected:
    std::vector<std::string> m_matiere; // liste des matiere
	std::vector<std::string> m_enseignant; // liste des prof
	std::vector<std::string> m_heure; //liste des jours de la semaine
	std::vector<std::string> m_jour; // liste des jours possible
	std::vector<std::string> m_activiter; // activiter déjà placer à revoir //TODOOOOOOOOOOOOOOOOOOOOOOOO
	std::vector<std::vector<std::vector<std::string>>> m_groupe; //liste des groupes des etudiants avec les matieres lier au groupe et le nombre  d'etudiant [nomGroupe,nbrEtudiant,Matiere]
	std::vector<std::string> m_salle; // liste des salles
	std::vector<std::vector<std::string>> m_Etudiant;// liste des etudiant avec leur matiere
	std::vector<std::string> m_filiere; //liste des filiere
	std::vector<float> m_horaire; // de la forme [heureMatinDepart, heureMainFin, HeureApremDEb, HeureApremFin]

  };




}
#endif // WRITE_H_INCLUDED
