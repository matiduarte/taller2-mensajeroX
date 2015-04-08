#include "Mensaje.h"

Mensaje::Mensaje(){
}

Mensaje::Mensaje(string cuerpo, string idUsuarioEmisor, string fecha){
	this->cuerpo = cuerpo;
	this->idUsuarioEmisor = idUsuarioEmisor;
	this->fecha = fecha;
}

Mensaje::~Mensaje(){
}

string Mensaje::serializar(){
	Json::Value mensaje;

	mensaje[keyCuerpo] = this->getCuerpo();
	mensaje[keyIdUsuarioEmisor] = this->getIdUsuarioEmisor();
	mensaje[keyFecha] = this->getFecha();

	string str_mensaje = mensaje.toStyledString();

	return str_mensaje;
}

int Mensaje::deserealizar(string aDeserealizar){
	Json::Value mensaje;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(aDeserealizar, mensaje);

	if(parseoExitoso){
		this->setCuerpo(mensaje.get(keyCuerpo, keyDefault).asString());
		this->setIdUsuarioEmisor(mensaje.get(keyIdUsuarioEmisor, keyDefault).asString());
		this->setFecha(mensaje.get(keyFecha, keyDefault).asString());
	} else{
		Loger::getLoger()->warn("no se pudieron deserializar los datos correctamente del mensaje");
	}

	Loger::getLoger()->guardarEstado();

	return parseoExitoso;
}

string  Mensaje::getCuerpo(){
	return this->cuerpo;
}

string  Mensaje::getIdUsuarioEmisor(){
	return this->idUsuarioEmisor;
}

string  Mensaje::getFecha(){
	return this->fecha;
}

void Mensaje::setCuerpo(string cuerpo){
	this->cuerpo = cuerpo;
}

void Mensaje::setIdUsuarioEmisor(string idUsuarioEmisor){
	this->idUsuarioEmisor = idUsuarioEmisor;
}

void Mensaje::setFecha(string fecha){
	this->fecha = fecha;
}

