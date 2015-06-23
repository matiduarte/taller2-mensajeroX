import unittest
from testCases import TestUsuario
from testCases import TestConversacion

if __name__ == '__main__':
	suite = unittest.TestSuite()

	tests = unittest.TestLoader().loadTestsFromTestCase(TestUsuario)
	suite.addTests(tests)

	tests = unittest.TestLoader().loadTestsFromTestCase(TestConversacion)
	suite.addTests(tests)

	unittest.TextTestRunner().run(suite)

