#include <stdio.h>
#include <stdlib.h>
#include "QueueInterface.h"

int main(void) {
	Queue Q;
	InitializeQueue(&Q);
	int remove;
	if (Empty(&Q) == 1) {
		printf("The Queue is empty \n") ;
	}
	else {
                printf("Some Tests Failed \n");
                return -1;
        }
	if (Remove(&Q, &remove) == 0) {
		printf("The Queue is empty \n");
	}
	else {
		printf("Some Tests Failed \n");
		return -1;
	}
	if (Empty(&Q) == 1) {
		printf("The Queue is empty \n");
	}
	else {
                printf("Some Tests Failed \n");
                return -1;
        }
	int i;
	for(i = 7 ; i > 0 ; i--) {
		Insert(i, &Q);
	}
	for(i = 7 ; i > 0 ; i--) {
		if (Remove(&Q, &remove) == 1) {
			if ( i != remove) {
				printf("Incorrect Value");
			} 
		}
		else {
                	printf("Some Tests Failed \n");
                	return -1;
        	}
	}
	if (Empty(&Q) ==  1) {
		printf("The Queue is empty\n");
	}
	else {
                printf("Some Tests Failed\n");
                return -1;
        }
	printf("Tests Successful\n");
	
}
