import unittest
from testCases import TestRestApi

if __name__ == '__main__':
	suite = unittest.TestLoader().loadTestsFromTestCase(TestRestApi)
	unittest.TextTestRunner().run(suite)
