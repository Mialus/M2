#include <cvsXlsToFet/write.h>
#include <iostream>
#include <vector>
#include <fstream>

namespace cvsXlsToFet {

  /*int Tower::getDegat(){
    return m_degat;
  }

  int Tower::getPosX(){
    return m_posX;
  }

  int Tower::getPosY(){
    return m_posY;
  }*/

	write::write(std::vector<char*> matiere, std::vector<char*> salle, std::vector<std::vector<char*>> Etudiant, std::vector<char*> filiere)
	{
		m_matiere = matiere;
		m_salle = salle;
		m_Etudiant = Etudiant;
		m_filiere = filiere;
	}

	write::~write() {}

	int write::writeInFet()
	{
		int i = sizeof(m_groupe) / sizeof(std::vector<std::vector<char*>>);
		char* mot = "test";
		creationGoupeEtudiant();

		/*std::ofstream objetfichier("cvsXlsToFet/emploi.fet", ios::out | ios::trunc); //on ouvre le fichier en ecriture

		if (objetfichier.bad()) //permet de tester si le fichier s'est ouvert sans probleme
			return 1;

		while (1) {
		
			objetfichier.seekg(0, ios::end);
			objetfichier.write(reinterpret_cast<const char *>(mot), 13);
		}
		//objetfichier <<"contenu du fichier" << endl;//*
		objetfichier.close();*/ //on ferme le fichier pour liberer la mémoire
		return 0;
	}

	void write::creationGoupeEtudiant()
	{
	}

}
