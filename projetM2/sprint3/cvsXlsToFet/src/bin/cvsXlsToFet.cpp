#include <cvsXlsToFet\readAndStock.h>
#include <cvsXlsToFet\read.h>
#include <cvsXlsToFet\write.h>
#include <boost\filesystem.hpp>


namespace cv = cvsXlsToFet;
namespace fs = boost::filesystem;

int main(int argc, char *argv[]) {

	//load ressource
	fs::path bindir_path(argv[0]);
	bindir_path = bindir_path.parent_path();
	fs::path datadir_path = bindir_path / fs::path("\\res\\fet");

	cout << "Path: " << datadir_path << std::endl;
}

