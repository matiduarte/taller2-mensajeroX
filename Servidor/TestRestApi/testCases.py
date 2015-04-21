import requests
import unittest

class TestRestApi(unittest.TestCase):
	def __init__(self, *args, **kwargs):
		super(TestRestApi, self).__init__(*args, **kwargs)
		self.__api_base_url = "http://localhost:8080/"
		self.__user_url = "/user"

	def test_consultar_usuario(self):
		payload = {'params': '{"nombre":"pepe"}'}
		r = requests.post(self.__api_base_url + "consultarUsuarioOnline", data=payload)

		self.assertEqual(r.status_code, 200)
		self.assertEqual(r.text, "prueba respuesta")

