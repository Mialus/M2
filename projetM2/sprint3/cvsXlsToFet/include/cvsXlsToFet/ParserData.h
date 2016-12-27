#ifndef PARSERDATA_H
#define PARSERDATA_H

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <functional>

using namespace std;

namespace cvsXlsToFet {

	class ParserData
	{
	public:
		ParserData();
		vector<string> getVectUE();
		vector< vector<string> > getEtudiantPerUe();
		vector< vector<string> >  getUeContrainte();

	private:
		vector<string> vect_ue;
		vector<string> vect_etudiant;
		vector<string> vect_ue_sans_doublon;
		vector< vector<string> > etudiantPerUe;
		vector< vector<string> >  ueContrainte;
	};
}
#endif
