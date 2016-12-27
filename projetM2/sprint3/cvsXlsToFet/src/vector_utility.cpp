#include <cvsXlsToFet/vector_utility.h>

namespace cvsXlsToFet {

	// Return true si le vector vect contien l'élément s
	bool vector_utility::containsString(std::vector<std::string> vect, std::string s) {
		for (int unsigned i = 0;i < vect.size();i++) {
			if (vect[i] == s) {
				return true;
			}
		}
		return false;
	}

	// Return true si vect1 et vect2 n'ont pas d'élément en commun
	bool vector_utility::intersectionVide(std::vector<std::string> vect1, std::vector<std::string> vect2) {
		for (int unsigned i = 0;i < vect1.size();i++) {
			if (containsString(vect2, vect1[i])) {
				return false;
			}
		}
		return true;
	}

	// return un vector qui est une copie sans doubon du vector en parametre
	std::vector<std::string> vector_utility::genVect_noDoublon(std::vector<std::string> vect) {
		std::vector<std::string> vect_ue;
		bool add = true;
		vect_ue.push_back(vect[0]);
		for (int unsigned i = 1;i < vect.size();i++) {
			for (int unsigned j = 0;j < vect_ue.size();j++) {
				if (vect_ue[j] == vect[i]) {
					add = false;
					break;
				}
			}
			if (add == true) {
				vect_ue.push_back(vect[i]);
			}
			add = true;
		}
		return vect_ue;
	}

	//Return le nombre de fois que vect contient s
	int vector_utility::nbrIter(std::vector<std::string> vect, std::string s) {
		int res = 0;
		for (int unsigned i = 0;i < vect.size();i++) {
			if (vect[i] == s) {
				res++;
			}
		}
		return res;
	}

	// Split s dans un v via le char c
	void vector_utility::split(const std::string& s, char c, std::vector<std::string>& v) {
		std::string::size_type i = 0;
		std::string::size_type j = s.find(c);

		while (j != std::string::npos) {
			v.push_back(s.substr(i, j - i));
			i = ++j;
			j = s.find(c, j);

			if (j == std::string::npos)
				v.push_back(s.substr(i, s.length()));
		}
	}
}