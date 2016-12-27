#ifndef PARSER_UTILITY_H_INCLUDED
#define PARSER_UTILITY_H_INCLUDED

#include "vector_utility.h"

namespace cvsXlsToFet {

	class parser_utility {
	public:

		//Utilitaire pour générer un vector contenant pour chaque élément un vector de la liste des élèves pour l'ue correspondante à l'ue du meme indice dans UE
		std::vector<std::vector<std::string>>  genEtudiantPerUe(std::vector<std::string> vectFichierEtudiant, std::vector<std::string> vectFichierUE, std::vector<std::string> UE);

		// On regarde quelle ue ne peut pas se faire en meme temps que quelle ue
		std::vector<std::vector<std::string> >  genUeContrainte(std::vector<std::vector<std::string> >  etudiantPerUe, std::vector<std::string> UE);
	}
}

#endif // PARSER_UTILITY_H_INCLUDED