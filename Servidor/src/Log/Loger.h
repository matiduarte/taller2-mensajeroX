/*
 * Loger.h
 *
 *  Created on: 2/4/2015
 *      Author: matias
 */

#ifndef SRC_LOG_LOGER_H_
#define SRC_LOG_LOGER_H_

#include<fstream>
#include<string.h>
#include <time.h>
#include "../constantes.h"
#include "../Utilidades/StringUtil.h"

class Loger {
private:

	static Loger* logInstancia;
	Loger();
	ofstream* archivo;
	void ponerFecha();
	void escribir(string texto);
	string nivel;
	string getNivel();
	void setNivel(string nivel);

public:
	static Loger* getLoger();
	void error(string texto);
	void warn(string texto);
	void info(string texto);
	void debug(string texto);
	void guardarEstado();
	void modificarNivel(string nivel);
	~Loger();
};

#endif /* SRC_LOG_LOGER_H_ */
