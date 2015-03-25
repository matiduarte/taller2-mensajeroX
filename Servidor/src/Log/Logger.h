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

using namespace std;

class Logger{

private:

	static Logger* logInstancia;
	Logger();
	ofstream* archivo;
	void ponerFecha();
	void escribir(string texto);

public:
	static Logger* getLogger();
	void error(string texto);
	void warm(string texto);
	void info(string texto);
	void debug(string texto);
	void guardarEstado();
	~Logger();

};

#endif /* SRC_LOG_LOGGER_H_ */
