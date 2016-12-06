#include <cvsXlsToFet\readAndStock.h>
#include <cvsXlsToFet\read.h>
#include <cvsXlsToFet\write.h>
#include <boost\filesystem.hpp>
#include <iostream>
#include <boost/thread/mutex.hpp>

using namespace std;

namespace cv = cvsXlsToFet;
namespace fs = boost::filesystem;

int main(int argc, char *argv[]) {

	//Base en attente de read

	//création du fichier en écriture
	//cv::write w;

	//load ressource
	fs::path bindir_path(argv[0]);
	bindir_path = bindir_path.parent_path();
	fs::path datadir_path = bindir_path / fs::path("\\res\\fet");

	cout << "Path: " << datadir_path << std::endl;
	std::cout << "Bonjour ! Je marche !" << std::endl;
}

