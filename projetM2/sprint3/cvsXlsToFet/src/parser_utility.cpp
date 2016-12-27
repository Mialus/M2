#include <cvsXlsToFet/parser_utility.h>
#include <cvsXlsToFet/vector_utility.h>

namespace cvsXlsToFet {

	//Utilitaire pour générer un vector contenant pour chaque élément un vector de la liste des élèves pour l'ue correspondante à l'ue du meme indice dans UE
	std::vector<std::vector<std::string>>  parser_utility::genEtudiantPerUe(std::vector<std::string> vectFichierEtudiant, std::vector<std::string> vectFichierUE, std::vector<std::string> UE) {
		std::vector<std::vector<std::string>> Mat;
		for (int unsigned i = 0;i < UE.size();i++) {
			std::vector<std::string> listStudent;
			for (int j = 0;j < vectFichierEtudiant.size();j++) {
				if (vectFichierUE[j] == UE[i]) {
					listStudent.push_back(vectFichierEtudiant[j]);
				}
			}
			Mat.push_back(listStudent);
		}
		return Mat;
	}

	// On regarde quelle ue ne peut pas se faire en meme temps que quelle ue
	std::vector<std::vector<std::string>>  parser_utility::genUeContrainte(std::vector<std::vector<std::string> >  etudiantPerUe, std::vector<std::string> UE) {
		std::vector<std::vector<std::string>> Mat;
		for (int unsigned i = 0;i < UE.size();i++) {
			std::vector<std::string> listContraintes;
			//parcour de etudiantPerUe
			for (int unsigned j = 0;j < UE.size();j++) {
				if (vector_utility::intersectionVide(etudiantPerUe[i], etudiantPerUe[j]) == false) {
					listContraintes.push_back(UE[j]);
				}
			}

			Mat.push_back(listContraintes);
		}
		return Mat;
	}
}
