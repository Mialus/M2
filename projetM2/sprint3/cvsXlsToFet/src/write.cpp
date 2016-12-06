#include <cvsXlsToFet/write.h>
#include <iostream>
#include <vector>
#include <fstream>
#include <string>

namespace cvsXlsToFet {

	write::write(std::vector<std::string> matiere, std::vector<std::string> salle, std::vector<std::vector<std::string>> etudiant, std::vector<std::string> filiere)
	{
		m_matiere = matiere;
		m_salle = salle;
		m_Etudiant = etudiant;
		m_filiere = filiere;
	}

	write::~write(){}


	int write::writeInFet()
	{
		int i = 0;
		int j = sizeof(m_groupe) / sizeof(std::vector<std::vector<char*>>);
		char* mot = "test";
		creationGoupeEtudiant();

		std::ofstream fichier("cvsXlsToFet/emploi.fet", std::ios::out | std::ios::trunc); //on ouvre le fichier en ecriture

		if (fichier) { //permet de tester si le fichier s'est ouvert sans probleme
			std::cout << "Impossible d'ouvrir le fichier !" << std::endl;
			return 1;
		}

			std::cout << "Le fichier existe bien et est correctement ouvert !" << std::endl;


			//écriture des champs constant
			fichier << "<?xml version=\"1.0\" encoding=\"UTF - 8\"?>" << std::endl;
			fichier << "<fet version=\"5.30.3\">" << std::endl;
			fichier << "<Institution_Name>Département de chez moi</Institution_Name>" << std::endl;
			fichier << "<Comments>Emplois du temps Examen : Année Universitaire 2016/2017</Comments>" << std::endl;

			//création des jours de la semaine
			fichier << "<Days_List>" << std::endl;
			fichier << "<Number_of_Days>" << m_jour.size() << "</Number_of_Days>" << std::endl;


			while (i<m_jour.size()) {
				fichier << "<Day><Name>" << m_jour.at(i) << "</Name></Day>" << std::endl;
				i++;
			}

			fichier << "</Days_List>" << std::endl;

			//création des heures possibles
			fichier << "<Hours_List>" << std::endl;
			fichier << "<Number_of_Hours>" << m_heure.size() << "</Number_of_Hours>" << std::endl;

			i = 0;
			while (i<m_heure.size()) {
				fichier << "<Hour><Name>" << m_heure.at(i) << "</Name></Hour>" << std::endl;
				i++;
			}

			fichier << "</Hours_List>" << std::endl;

			//liste des sujets
			fichier << "<Subjects_List>" << std::endl;

			i = 0;
			while (i<m_matiere.size()) {
				fichier << "<Subjects><Name>" << m_matiere.at(i) << "</Name>" << std::endl;
				fichier << "<Comments></Comments></Subjects>" << std::endl;
				i++;
			}

			fichier << "</Subjects_List>" << std::endl;

			// nom des activité <Activity_Tags_List>
			fichier << "<Activity_Tag><Name>Examen</Name><Comments></Comments></Activity_Tag></Activity_Tags_List>" << std::endl;

			//Liste des prof
			fichier << "<Teachers_List>" << std::endl;

			i = 0;
			while (i<m_enseignant.size()) {
				fichier << "<Teacher><Name>" << m_enseignant.at(i) << "</Name>" << std::endl;
				fichier << "<Target_Number_of_Hours>0</Target_Number_of_Hours>" << std::endl;
				fichier << "<Qualified_Subjects>" << std::endl;
				fichier << "</Qualified_Subjects>" << std::endl;
				fichier << "<Comments></Comments></Teacher>" << std::endl;
				i++;
			}

			fichier << "</Teachers_List>" << std::endl;

			//Etudiant Nom groupe
			fichier << "<Students_List>" << std::endl;

			i = 0;
			while (i<m_groupe.size()) {
				fichier << "<Year><Name>" << m_groupe.at(i) << "</Name>" << std::endl;
				fichier << "<Number_of_Students>" << m_groupe[i].at(i) << "</Number_of_Students>" << std::endl;
				fichier << "<Comments></Comments></Year>" << std::endl;
				i++;
			}

			fichier << "</Students_List>" << std::endl;

			//Activité bloqué (les cours déjà placé)
			fichier << "<Activities_List>" << std::endl;

			i = 0;
			while (i<m_activiter.size()) {
				fichier << "<Teacher>" << m_activiter.at(i) << "</Teacher>" << std::endl;
				fichier << "<Number_of_Students>" << m_activiter.at(i) << "</Number_of_Students>" << std::endl;
				fichier << "<Subject></Subject> " << std::endl;
				fichier << "<Activity_Tag></Activity_Tag>" << std::endl;
				fichier << "<Students></Students>" << std::endl;
				fichier << "<Duration></Duration>" << std::endl;
				fichier << "<Total_Duration></Total_Duration>" << std::endl;
				fichier << "<Id></Id>" << std::endl;
				fichier << "<Activity_Group_Id></Activity_Group_Id>" << std::endl;
				fichier << "<Active></Active>" << std::endl;
				fichier << "<Comments></Comments></Activity>" << std::endl;
				i++;
			}

			fichier << "</Activities_List>" << std::endl;

			//Liste des batiments // on verra ce qu'on en fait, pour le moment je met les filières dedans ^^
			fichier << "<Buildings_List>" << std::endl;

			i = 0;
			while (i<m_filiere.size()) {
				fichier << "<Building><Name>" << m_filiere.at(i) << "</Name>" << std::endl;
				fichier << "<Comments></Comments></Building>" << std::endl;
				i++;
			}

			fichier << "</Students_List>" << std::endl;

			//Liste des Salles
			fichier << "<Rooms_List>" << std::endl;

			i = 0;
			while (i<m_salle.size()) {
				fichier << "<Room><Name>" << m_salle.at(i) << "</Name>" << std::endl;
				fichier << "<Building></Building>" << std::endl;
				fichier << "<Capacity></Capacity>" << std::endl;
				fichier << "<Comments></Comments></Room>" << std::endl;
				i++;
			}

			fichier << "</Students_List>" << std::endl;

			//Horaire de pause
			fichier << "<Time_Constraints_List>" << std::endl;
			fichier << "<ConstraintBasicCompulsoryTime>" << std::endl;
			fichier << "<Weight_Percentage>100</Weight_Percentage>" << std::endl;
			fichier << "<Active>true</Active>" << std::endl;
			fichier << "<Comments></Comments>" << std::endl;
			fichier << "</ConstraintBasicCompulsoryTime>" << std::endl;

			fichier << "<ConstraintBreakTimes>" << std::endl;
			fichier << "<Weight_Percentage>100</Weight_Percentage>" << std::endl;
			fichier << "<Number_of_Break_Times>" << m_jour.size() << "</Number_of_Break_Times>" << std::endl;

			i = 0;
			while (i<m_jour.size()) {

				fichier << "<Break_Time><Day>" << m_jour.at(i) << "</Day>" << std::endl;
				fichier << "<Hour>12:30</Hour>" << std::endl;
				fichier << "</Break_Time>" << std::endl;
				i++;
			}
			fichier << "<Comments></Comments>" << std::endl;
			fichier << "</ConstraintBreakTimes>" << std::endl;

			//Début des épreuves // A continuer voir le schéma de ET2012

			i = 0;
			while (i<m_salle.size()) {
				fichier << "<ConstraintActivitiesSameStartingTime>" << std::endl;
				fichier << "<Weight_Percentage>100</Weight_Percentage>" << std::endl;
				fichier << "<Room><Name>" << m_salle.at(i) << "</Name>" << std::endl;
				fichier << "<Building></Building>" << std::endl;
				fichier << "<Capacity></Capacity>" << std::endl;
				fichier << "<Comments></Comments></Room>" << std::endl;
				i++;
			}

			fichier << "</ConstraintActivitiesSameStartingTime>" << std::endl;



		/*while (1) {

			char car = 'S';
			fichier.put(car);

			//objetfichier.seekg(0, std::ios::end);
			//objetfichier.write(reinterpret_cast<const char *>(mot), 13);
		}*/
		//objetfichier <<"contenu du fichier" << endl;//*
		fichier << "</fet>" << std::endl;
		fichier.close(); //on ferme le fichier pour liberer la mémoire
		return 0;
	}

	void write::creationGoupeEtudiant(){}
}
