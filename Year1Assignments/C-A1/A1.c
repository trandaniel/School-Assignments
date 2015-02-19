#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define MAX 12
#define PER 2
#define TOL 0.01

//compiled with -o -lm -std=c99

int NextDensities (double fromGrid[MAX][MAX], double toGrid[MAX][MAX]);
void PrintGrid (double grid[MAX][MAX]);

int main(int argc, char *argv[]) {
	int years;
	int currentYear = 0;
	int state = 0;
	FILE *input;
	double fromGrid[MAX][MAX];
	double toGrid[MAX][MAX];

	if (argc != 3) {
		fprintf(stderr, "Please enter exactly 2 arguments\n");
		exit(-1);
	} else {
		years = atoi(argv[1]);
		if (years == 0) {
			fprintf(stderr, "Please make sure you"
				 " entered a nonzero integer and file\n");
			exit(-1);
		}
		input = fopen(argv[2], "r");
		if (input == 0) {
			fprintf(stderr, "Cannot open or find file\n");
			exit(-1);
		}
	}	
	double value;
	for (int i = 0 ; i < MAX ; i++) {
		for (int j = 0 ; j < MAX ; j++) {
			fscanf(input, "%lf", &value);
			if (value == EOF) {
				break;
			}
			fromGrid[i][j] = value;
			toGrid[i][j] = value;
			//printf("%.1f ", fromGrid[i][j]);
		}
		if (value == EOF) {
			break;
		}
	}
	
	do {
		//print the grid and current year
		printf("The current Year is %d\n", currentYear);
		if (currentYear % 2 == 0) {
			PrintGrid(fromGrid);
			//find the next density
			state = NextDensities(fromGrid, toGrid);
		} else {
			PrintGrid(toGrid);
			state = NextDensities(toGrid, fromGrid);
		}
		//year has passed
		currentYear++;
	} while (currentYear <= years && state == 0);
	//if it has reached steady state..
	if (state == 1) {
		printf("The current Year is %d\n", currentYear);
		if (currentYear % 2 == 0) {
			PrintGrid(fromGrid);
		} else {
			PrintGrid(toGrid);
		}
		//print steady state year
		printf("Steady state reached in %d years\n", currentYear);
	}
}

//function to calculate following years density
int NextDensities (double fromGrid[MAX][MAX], double toGrid[MAX][MAX]) {
double density;
	//calculate average
	for (int i = 2 ; i < MAX - PER ; i++) {
		for (int j = 2 ; j < MAX - PER ; j++) {
			density = (fromGrid[i][j] + fromGrid[i - 1][j] + 
				fromGrid[i][j + 1] + fromGrid[i + 1][j] +
				fromGrid[i][j - 1]) / 5;
			toGrid[i][j] = density;
		}
	}
	//check if it is steady state
	for (int i = 2 ; i < MAX - PER ; i++) {
		for (int j = 2 ; j < MAX - PER ; j++) {
			if (fabs(fromGrid[i][j] - toGrid[i][j]) <= TOL) {
				continue;
			}
			else {
				return 0;
			}
		}
	}
return 1;
}	

//function to print the grid
void PrintGrid (double grid[MAX][MAX]) {
        for (int i = 0 ; i < MAX ; i++) {
                for (int j = 0 ; j < MAX ; j++) {
                        printf("%.1f " , grid[i][j]);
                }
                printf("\n");
        }
}
