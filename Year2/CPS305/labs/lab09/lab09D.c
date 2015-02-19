#include <stdio.h>
#include "OpenAddrHash.h"

int h(KeyType K) {
	return K%M;
}

int p(KeyType K) {
	printf("collision\n");
	return K/M;
}

void LookFor(Table T, KeyType K) { 
	int found;
	found=HashSearch(T,K);
	if (found == -1) printf("key %d not found\n",K);
	else printf("key %d found in slot %d\n",K,found);
}

int main(void) {
	int found;
	Table T;
	InitializeTable(T);
	HashInsert(T,9, 'a');
	HashInsert(T,61,'b');
	HashInsert(T,95,'c');
	HashInsert(T,78,'d');
	HashInsert(T,44,'e');
	PrintTable(T);
	LookFor(T,44);
	LookFor(T,58);
	return 0;
}
