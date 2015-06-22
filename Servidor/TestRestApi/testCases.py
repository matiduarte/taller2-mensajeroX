import requests
import unittest
import json
import ConfigParser

class TestUsuario(unittest.TestCase):
	def __init__(self, *args, **kwargs):
		super(TestUsuario, self).__init__(*args, **kwargs)
		self.__api_base_url = "http://localhost:8080/"
		self._url_usuario = "usuario/"
		self._url_usuario_conversacion = "usuarioConversacion/"
		

	def setUp(self):
		config = ConfigParser.RawConfigParser()
		config.read('restTest.cfg')
		self._telefonoUltimoUsuario = config.getint('general', 'telefonoUltimoUsuario')
		self._tokenUltimoUsuario = config.get('general', 'tokenUltimoUsuario')


	def test_1registrar_usuario(self):
		payload = {'Telefono': self._telefonoUltimoUsuario+1, 'Nombre': 'usuarioTest', 'FotoDePerfil':'foto', 'Password':'123456'}
		r = requests.post(self.__api_base_url + self._url_usuario, data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")

		#Actualizo el config con el ultimo telefono
		config = ConfigParser.RawConfigParser()
		config.read('restTest.cfg')
		config.set('general', 'telefonoUltimoUsuario', self._telefonoUltimoUsuario+1)
		with open('restTest.cfg', 'wb') as configfile:
   			config.write(configfile)
	
	def test_2usuario_ya_registrado(self):
		payload = {'Telefono': self._telefonoUltimoUsuario, 'Nombre': 'usuarioTest', 'FotoDePerfil':'foto', 'Password':'123456'}
		r = requests.post(self.__api_base_url + self._url_usuario, data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "false")

	def test_3obtener_usuario(self):
		r = requests.get(self.__api_base_url + self._url_usuario + str(self._telefonoUltimoUsuario))
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")

		#Actualizo el config con el token
		config = ConfigParser.RawConfigParser()
		config.read('restTest.cfg')
		data = json.loads(data["payload"])
		config.set('general', 'tokenUltimoUsuario', data["Token"])
		with open('restTest.cfg', 'wb') as configfile:
   			config.write(configfile)

	def test_4obtener_usuario_incorrecto(self):
		r = requests.get(self.__api_base_url + self._url_usuario + str(self._telefonoUltimoUsuario + 1000))
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "false")

	def test_5modificar_usuario(self):
		payload = {'Telefono': self._telefonoUltimoUsuario, 'Nombre': 'usuarioTest', "Token": self._tokenUltimoUsuario}
		r = requests.put(self.__api_base_url + self._url_usuario, data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")

	def test_6obtener_conversaciones_usuario(self):
		r = requests.get(self.__api_base_url + self._url_usuario_conversacion + str(self._telefonoUltimoUsuario) + "?idsConversaciones=''")
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")



class TestConversacion(unittest.TestCase):
	def __init__(self, *args, **kwargs):
		super(TestConversacion, self).__init__(*args, **kwargs)
		self.__api_base_url = "http://localhost:8080/"
		self._url_conversacion = "conversacion/"	

	def setUp(self):
		config = ConfigParser.RawConfigParser()
		config.read('restTest.cfg')
		self._telefonoUltimoUsuario = config.getint('general', 'telefonoUltimoUsuario')
		self._tokenUltimoUsuario = config.get('general', 'tokenUltimoUsuario')


	def test_1almacenar_conversacion(self):
		payload = {'IdUsuarioReceptor': self._telefonoUltimoUsuario-1, 'IdUsuarioEmisor': self._telefonoUltimoUsuario, "Token": self._tokenUltimoUsuario}
		r = requests.post(self.__api_base_url + self._url_conversacion, data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")
	
	def test_2almacenar_conversacion_token_incorrecto(self):
		payload = {'IdUsuarioReceptor': self._telefonoUltimoUsuario, 'IdUsuarioEmisor': self._telefonoUltimoUsuario-1, "Token": "aa"}
		r = requests.post(self.__api_base_url + self._url_conversacion, data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "false")


	def test_3obtener_id_conversacion(self):
		payload = {'TelefonoReceptor': self._telefonoUltimoUsuario-1, 'TelefonoEmisor': self._telefonoUltimoUsuario}
		r = requests.get(self.__api_base_url + self._url_conversacion + 'id/', data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")

	def test_4obtener_id_conversacion_incorrecto(self):
		payload = {'TelefonoReceptor': self._telefonoUltimoUsuario+1000, 'TelefonoEmisor': self._telefonoUltimoUsuario+9999}
		r = requests.get(self.__api_base_url + self._url_conversacion + 'id/', data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "false")

	def test_5obtener_conversacion(self):
		payload = {'TelefonoReceptor': self._telefonoUltimoUsuario-1, 'TelefonoEmisor': self._telefonoUltimoUsuario}
		r = requests.get(self.__api_base_url + self._url_conversacion + 'id/', data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		idConversacion = data["payload"]

		payload = {'IdUltimoMensaje': ''}
		r = requests.get(self.__api_base_url + self._url_conversacion + idConversacion, data=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["success"], "true")

	

