#ifndef VECTOR_UTILITY_H_INCLUDED
#define VECTOR_UTILITY_H_INCLUDED

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <functional>

namespace cvsXlsToFet {

	class vector_utility {
	public:

		// Return true si le vector vect contien l'élément s
		bool containsString(std::vector<std::string> vect, std::string s);

		// Return true si vect1 et vect2 n'ont pas d'élément en commun
		bool intersectionVide(std::vector<std::string> vect1, std::vector<std::string> vect2);

		// return un vector qui est une copie sans doubon du vector en parametre
		std::vector<std::string> genVect_noDoublon(std::vector<std::string> vect);

		//Return le nombre de fois que vect contient s
		int nbrIter(std::vector<std::string> vect, std::string s);

		// Split s dans un v via le char c
		void split(const std::string& s, char c, std::vector<std::string>& v);
	}
}

#endif // VECTOR_UTILITY_H_INCLUDED