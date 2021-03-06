/*
 * Persistible.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_ENTIDADES_PERSISTIBLE_H_
#define SRC_ENTIDADES_PERSISTIBLE_H_

#include <string.h>
#include <iostream>
#include "../constantes.h"

using namespace std;

class Persistible {
public:
	Persistible();
	virtual ~Persistible();
	virtual string serializar();
	virtual int deserealizar(string aDeserealizar);
	virtual void persistir();
	static Persistible* obtener(string clave);
	static void eliminar(string clave);
	virtual string getId();
};

#endif /* SRC_ENTIDADES_PERSISTIBLE_H_ */
