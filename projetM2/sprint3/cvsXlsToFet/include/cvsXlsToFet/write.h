#ifndef WRITE_H_INCLUDED
#define WRITE_H_INCLUDED

#include <vector>

namespace cvsXlsToFet {

  class write {
  public:
    write(std::vector<char*> matiere, std::vector<char*> salle, std::vector<std::vector<char*>> Etudiant, std::vector<char*> filiere);
    ~write();

    //ecris dans le fet
    int writeInFet();
    
    //cre des groupes d'etudiant en fonction de leur contrainte 
    void creationGoupeEtudiant();

  protected:
    std::vector<char*> m_matiere; // liste des matiere
	std::vector<char*> m_jour; // liste des jours possible
	std::vector<std::vector<char*>> m_groupe; //liste des groupes des etudiants avec les matieres lier au groupe
	std::vector<char*> m_salle; // liste des salles
	std::vector<std::vector<char*>> m_Etudiant;// liste des etudiant avec leur matiere
	std::vector<char*> m_filiere; //liste des filiere
	std::vector<float> m_horaire; // de la forme [heureMatinDepart, heureMainFin, HeureApremDEb, HeureApremFin]

  };




}
#endif // WRITE_H_INCLUDED
