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

using namespace std;

class StringUtil {
public:
	StringUtil();
	virtual ~StringUtil();
    static string toLower(string str);
    static string toUpper(string str);
};

#endif /* SRC_UTILIDADES_STRINGUTIL_H_ */
