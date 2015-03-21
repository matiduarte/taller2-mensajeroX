#include <assert.h>
#include "rocksdb/db.h"
#include "stdio.h"

int main(int argc, char** argv) {
	rocksdb::DB *db;
	rocksdb::Options options;
	options.create_if_missing = true;
	rocksdb::Status status = rocksdb::DB::Open(options, "/tmp/testdb/", &db);
	assert(status.ok());
	return 0;
}
