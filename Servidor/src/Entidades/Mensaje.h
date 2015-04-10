/*
 * Persistible.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_ENTIDADES_MENSAJE_H_
#define SRC_ENTIDADES_MENSAJE_H_

#include <string.h>
#include <iostream>
#include "../constantes.h"
#include "Persistible.h"
#include "../Utilidades/md5.h"
#include "../json/json.h"
#include "../Log/Loger.h"

using namespace std;

class Mensaje : public Persistible{
private:
	string cuerpo;
	string idUsuarioEmisor;
	string fecha;
	string id;
public:
	Mensaje();
	Mensaje(string cuerpo, string idUsuarioEmisor, string fecha);
	Mensaje(string aDeserealizar);
	~Mensaje();
	string serializar();
	int deserealizar(string aDeserealizar);
	string getId();
	string getCuerpo();
	string getIdUsuarioEmisor();
	string getFecha();
	void setId(string id);
	void setCuerpo(string cuerpo);
	void setIdUsuarioEmisor(string idUsuarioEmisor);
	void setFecha(string fecha);
};

#endif /* SRC_ENTIDADES_PERSISTIBLE_H_ */
