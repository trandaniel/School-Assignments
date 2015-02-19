#include <stdio.h>
#include <stdlib.h>

int main (void) {
	int prod;
	printf("Testing base case \n");	
	prod = Prod(2,2);
	printf("%d \n", prod);
	prod = Prod(3,2);
	printf("%d \n", prod);
	printf("Testing recursion \n");
	prod = Prod(2,5);
	printf("Prod(2,5) is: %d \n", prod);
	prod = Prod(10,11);
	printf("Prod(10,11) is: %d \n", prod);
}
