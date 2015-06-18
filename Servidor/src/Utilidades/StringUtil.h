/*
 * StringUtil.h
 *
 *  Created on: 25/3/2015
 *      Author: matias
 */

#ifndef SRC_UTILIDADES_STRINGUTIL_H_
#define SRC_UTILIDADES_STRINGUTIL_H_

#include <string.h>
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
#include "../json/json.h"
#include "../constantes.h"

using namespace std;

class StringUtil {

public:
	StringUtil();
	virtual ~StringUtil();
    static string toLower(string str);
    static string toUpper(string str);
    static int str2int (string string);
    static vector<std::string>& split(const string &s, char delim, vector<string> &elems);
    static vector<std::string> split(const string &s, char delim);
    static bool toBoolean(string value);
    static string toString(bool value);
    static vector<string> jsonValueToVector(Json::Value vector);
    static bool vectorContiene(vector<string> vector, string valor);
    static char* str2Char (string string);
};

#endif /* SRC_UTILIDADES_STRINGUTIL_H_ */
