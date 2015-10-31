#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>

#include "poly_ADT.h"
int main(void) {
	poly *P1, *P2, *P3, *P4;
	P1 = poly_create(3, 1, 2, 3, 4, 5, 6);
	P2 = poly_create(0);
	P3 = poly_create(4, 1, 2, 3, 4, 5, 6, 7, 8);
	printf("P1: "); poly_print(P1);
	printf("P2: "); poly_print(P2);
	printf("P3: "); poly_print(P3);
	P4 = poly_scalar_mult(P1, 3);
	printf("P4: "); poly_print(P4);
}
