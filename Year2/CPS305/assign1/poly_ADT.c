#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include "poly_ADT.h"

poly *reverse(poly *P);

/*
* Function to reverse the terms so they are
* formatted from largest power to smallest
*/
poly *reverse(poly *P) {
	//create a poly to return
	poly *new;
	//allocate memory and store the num_terms of given poly
	new = (poly*) malloc(sizeof(poly));
	new->num_terms = P->num_terms;
	
	//create nodes to reverse terms
	node *terms = P->terms;
	node *first;
	//reverse the terms
	while (terms->next != NULL) {
		node *next = terms->next;
		terms->next = first;
		first = terms;
		terms = next;
	}
	//return the reversed poly
	new->terms = first;
	return new;
}
/*
* Function to print the polynomial entered in the parameter
* Exits and prints nothing if the polynomial contains nothing
*/
void poly_print(poly *P) {

	//if the polynominal is NULL, exit the function
        if (P == NULL) {
                printf("\n");
                return ;
        }
	
	// create a temporary node to view the values in the terms linked list
        node *nodeC;
        nodeC = (node*) malloc(sizeof(node));
        nodeC = P->terms;
	
	int i;
	//print each term while the next link is not empty
        for (i = 0 ; i < P->num_terms ; i++) {
                printf("%dx^%d", nodeC->coef, nodeC->powr);
                nodeC = nodeC->next;
		//if the next link is not empty print a +
                if (i < P->num_terms - 1) {
                        printf(" + ");
                }
        }
        printf("\n");

}

/*
* Function to free the memory of the polynominal and nodes
*/
void poly_free(poly **P) {
        free((*P)->terms);
        free(*P);
        *P = NULL;
}

/*
* Function to duplicate a polynominal by calling function poly_scalar_mult on the
* polynominal and multiplying it by 1, thus leaving the values unchaged but returning a new
* polynominal
*/
poly *poly_duplicate(poly *P) {
        return poly_scalar_mult(P, 1);
}

/*
* Function to add two polynominals together and return a new polynominal
*/

poly *poly_add(poly *P1, poly *P2) {
	return NULL;
}

/*
* Function to create a polynominal with terms using a linked list for each term and returning the pointer
*/
poly *poly_create(int num, ...) {
	//if there are no terms return null
	if (num == 0) 
		return NULL;
	int i;
	//creates the poly to return 
	poly *new;
	//allocate memory for polynominal to be returned
	new = (poly*) malloc(sizeof(poly));
	new->num_terms = num;
	//create the linked list
	node *first;
	//allocate memory
	first = (node*) malloc(sizeof(node));
	new->terms = first;
	//start the variable arguments list
	va_list list;
	va_start(list, num);
	
	//iterate through the list and place values in the nodes
	for (i = 0 ; i < num ; i++) {
		first->coef = va_arg(list, int);
		first->powr = va_arg(list, int);
		first->next = (node*) malloc(sizeof(node));
		first = first->next;
	}
	//end the list
	va_end(list);
	first->next = NULL;
	//return the polynominal created
	return reverse(new);
}

/*
* Function to multiply a polynominal by a value,
* returns null if the polynominal is multiplied by 0 or the 
* polynominal is empty
*/
poly *poly_scalar_mult(poly *P, int x) {
	//return an emptt polynominal if 
	//multipled by 0 or the polynomial is empty
        if (x == 0 || P == NULL)
                return NULL;
        poly *new;
        new = (poly*) malloc(sizeof(poly));
        node *first;
        first = (node*) malloc(sizeof(node));
	//create a temporaty node to check the values of the given polynominal
        node *temp = P->terms;

        new->num_terms = P->num_terms;
        temp = P->terms;

        new->terms = first;
        int i;

	//iterate through the polynominal 
        for (i = 0 ; i < (P->num_terms) ; i++) {
		node *next = temp->next;
                first->coef = (temp->coef) * x ;
                first->powr = temp->powr;
                temp = next;
                first->next = (node*) malloc(sizeof(node));
                first = first->next;
        }

        first->next = NULL;
	//return the multipled polynominal
        return new;
}

