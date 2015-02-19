#include <stdio.h>
#include <stdlib.h>

int Prod(int x, int y) {
	if (x > y) {
		return -1;
	}
	if (x == y) {
		return x;
	}
	
	return x * Prod(x + 1, y);
}
