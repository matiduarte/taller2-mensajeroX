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
