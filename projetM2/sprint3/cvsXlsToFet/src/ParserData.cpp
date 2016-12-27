#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <functional>

#include <cvsXlsToFet/parser_utility.h>
#include <cvsXlsToFet/ParserData.h>

using namespace std;

namespace cvsXlsToFet {

	ParserData::ParserData() {
		int i = 0;
		int skip = 0;
		vector<string> lect;
		vector<string> affinnage;
		ifstream fichier("LISTE_ETUDIANTS_POUR_les_examen_et_Ue.csv");
		if (fichier)
		{
			string ligne;
			getline(fichier, ligne);
			while (getline(fichier, ligne))
			{
				lect.clear();
				affinnage.clear();
				//cout << ligne << endl;
				vector_utility::split(ligne, ',', lect);
				// cout << ue[i] << endl;
				if (skip != 0) {
					vector_utility::split(lect[i + 1], '"', affinnage);
					vect_ue.push_back(affinnage[i + 1]);
					vector_utility::split(lect[i], '"', affinnage);
					vect_etudiant.push_back(affinnage[i]);
				}
				skip++;
			}
		}
		else
		{
			cout << " erreur" << endl;
		}
		vect_ue_sans_doublon = vector_utility::genVect_noDoublon(vect_ue);
		//cout<< nbrIter(vect_ue_sans_doublon,"JVM3INTE")<<endl;
		//cout<< vect_ue_sans_doublon.size()<<endl;
		//cout << "\n\n\n" <<endl;
		//cout << "\n\n\n" <<endl;
		//cout << vect_ue.size() <<endl;
		etudiantPerUe = parser_utility::genEtudiantPerUe(vect_etudiant, vect_ue, vect_ue_sans_doublon);
		ueContrainte = parser_utility::genUeContrainte(etudiantPerUe, vect_ue_sans_doublon);
	}

	vector<string> ParserData::getVectUE() { vect_ue_sans_doublon; }
	vector< vector<string> > ParserData::getEtudiantPerUe() { return etudiantPerUe; }
	vector< vector<string> >  ParserData::getUeContrainte() { return ueContrainte; }

}