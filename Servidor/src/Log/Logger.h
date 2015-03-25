/*
 * Logger.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_LOG_LOGGER_H_
#define SRC_LOG_LOGGER_H_

#include<fstream>
#include<string.h>
#include <time.h>
#include "../constantes.h"
#include "../Utilidades/StringUtil.h"

using namespace std;

class Logger{

private:

	static Logger* logInstancia;
	Logger();
	ofstream* archivo;
	void ponerFecha();
	void escribir(string texto);
	string nivel;
	string getNivel();
	void setNivel(string nivel);

public:
	static Logger* getLogger();
	void error(string texto);
	void warn(string texto);
	void info(string texto);
	void debug(string texto);
	void guardarEstado();
	void modificarNivel(string nivel);
	~Logger();

};

#endif /* SRC_LOG_LOGGER_H_ */
