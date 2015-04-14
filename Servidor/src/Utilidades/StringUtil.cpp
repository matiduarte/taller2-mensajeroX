/*
 * StringUtil.cpp
 *
 *  Created on: 25/3/2015
 *      Author: matias
 */

#include "StringUtil.h"

StringUtil::StringUtil() {
	// TODO Auto-generated constructor stub

}

StringUtil::~StringUtil() {
	// TODO Auto-generated destructor stub
}

string StringUtil::toLower(string str){
        char* lower = (char*)str.c_str();
        int i = 0;
        char c;

        while (str[i]){
                c = str[i];
                lower[i] = tolower(c);
                i++;
        }

        return (string)lower;
}

string StringUtil::toUpper(string str){
        char* upper = (char*)str.c_str();
        int i = 0;
        char c;

        while (str[i]){
                c = str[i];
                upper[i] = toupper(c);
                i++;
        }

        return (string)upper;
}

int StringUtil::str2int (string string) {
        std::istringstream ss(string.c_str());
        int i;
        ss >> i;
        return i;
}

vector<std::string>& StringUtil::split(const string &s, char delim, vector<string> &elems) {
    std::stringstream ss(s);
    std::string item;
    while(std::getline(ss, item, delim)) {
        elems.push_back(item);
    }
    return elems;
}

vector<std::string> StringUtil::split(const string &s, char delim) {
    std::vector<std::string> elems;
    return StringUtil::split(s, delim, elems);
}

bool StringUtil::toBoolean(string value){

	//TODO: VER SI VA A VENIR 0 O 1 O ONLINE U OFFLINE
	return (value == "1");
}

vector<string> StringUtil::jsonValueToVector(Json::Value vector){
	std::vector<std::string> result;
	for(unsigned i=0; i< vector.size();i++){
		string idActual = vector.get(i, keyDefault).asString();
		result.push_back(idActual);
	}

	return result;
}

bool StringUtil::vectorContiene(vector<string> vector, string valor){
	return find(vector.begin(), vector.end(), valor) != vector.end();
}
