#include <stdio.h>
#include <stdlib.h>
#include <sched.h>
#include <signal.h>
#include <unistd.h>

void printGrid();
void nextGen(int x, int y, int neighbours);
int checkPosition(int x, int y, int size);
int countNeighbours(int xUp, int yUp, int xDn, int yDn, int x, int y);
void preThreadGridSet();
void createNextGrid();
int loop();


int gridSize; //used to keep the size of the grid
int **grid; //used to store the previous generation of the grid
int **grid2; //used to store the next generation of the grid
int counter; //used to keep count of number of cells passed
int numCells; //the maximum number of cells, defined gridSize * gridSize
int split; //number of cells designated to each thread for an even split
int currentT = 0; //current thread, number 0...split-1
int upperR, lowerR; //lower and upper Range of numbers of a thread
int numThreads; //number of threads to be created
int *array; //used to store the PIDs of each thread
int *threads; //used to store the threads

int main(int argc, char *argv[]) {
	
	int generationCounter = 1;
	
	int i, j; //loop variables
	FILE *input; // file pointer
	
	
	//create the stack
	const int STACK_SIZE = 5536;
	char **stack;
	char **stackTop;

	numThreads = atoi(argv[2]);

	stack = malloc(numThreads * sizeof(char*));
	stackTop = malloc(numThreads * sizeof(char*));	
	for (i = 0 ; i < numThreads; i++) {
		stack[i] = (char*) malloc(STACK_SIZE * sizeof(char));
		
	}
	
	for (i = 0 ; i < numThreads; i++) {
		stackTop[i] = stack[i] + STACK_SIZE;
	}

	if (stack == NULL) {
		perror("malloc");
		exit(1);
	}//end stack creation
	
	input = fopen(argv[1], "r");
	//file input error checking
	if (input == NULL) {
		fprintf(stderr, "Cannot open file\n");
		exit(1);
	}
	
	fscanf(input, "%d", &gridSize);

	if (gridSize <= 3) {
		fprintf(stderr, "Grid size is too small\n");
		exit(1);
	}//end error checking
	
	// input from file to array
	grid = malloc(gridSize * sizeof(int *));
	for (i = 0 ; i < gridSize ; i++) {
		grid[i] = malloc(gridSize * sizeof(int));
	}
	
	grid2 = malloc(gridSize * sizeof(int *));
	for (i = 0 ; i < gridSize ; i++) {
		grid2[i] = malloc(gridSize * sizeof(int));
	}
	
	for (i = 0 ; i < gridSize ; i++) {
		for (j = 0 ; j < gridSize ; j++) {
			fscanf(input, "%d", &grid[i][j]);
		}
	}//end input from files to array

	fclose(input);	
	printf("Generation 0\n");
	printGrid(grid);
	sleep(1);
	numCells = gridSize * gridSize;
	split = numCells / numThreads;

	array = malloc(numThreads * sizeof(int));
	threads = malloc(numThreads * sizeof(int));
	
	for (i = 0 ; i < numThreads ; i++) {
		array[i] = i;
	}
	
	
	while(1) {
		for (i = 0 ; i < numThreads ; i++) {
		
			//spawn thread here
			threads[i] = clone(loop, stackTop[i], CLONE_VM | SIGCHLD, (void*)(&array[i]));
			usleep(50000);	
			currentT++;
		}	
		
		for (i = 0 ; i < numThreads ; i++) {
			waitpid(-1, threads[i], NULL);
		}
		system("clear");
		printf("Generation %d\n", generationCounter);
		preThreadGridSet();
		currentT = 0;
		generationCounter++;
	}
}

//function to be threaded to calculate grid
int loop() {
	int i;
	int thread = currentT;
	
		createNextGrid(thread);

		for (i = 0 ; i < numThreads ; i++) {
			usleep(50000);
			waitpid(-1, threads[i], NULL);
		}	
	//counter = 0;
	
}

//create the next generation grid
//grid is split by counting off cells using split, last thread takes remainder
void createNextGrid(int thread) {
	int counter = 1;
	lowerR = thread * split;
	upperR = (thread + 1) * split;
	if (thread == numThreads - 1) {
		upperR = numCells;
	}

	int i, j; //loop variables
	for ( i = 0 ; i < gridSize  ; i++) {
			for (j = 0 ; j < gridSize ; j++) {

				if (counter > upperR) {
					break;
				}
				
				if (thread == numThreads - 1) {
						nextGen(i, j, checkPosition(i, j, gridSize));
						counter++;
						continue;
					
				}
				
				else if (counter > lowerR && counter <= upperR) {
					nextGen(i, j, checkPosition(i, j, gridSize));
					counter++;
					continue;
				}
				
				else {
					counter++;
				}
			}
			if (counter > upperR) {
				break;
			}
	}
}

//set prev. generation grid to new generation grid (grid2)
void preThreadGridSet() {
	int y;
	int x;
	
	for (y = 0 ; y < gridSize ; y++) {
		for (x = 0 ; x < gridSize ; x++) {
			grid[x][y] = grid2[x][y];
		}
	}
	printGrid(grid2);
	
}

//prints the grid
void printGrid (int **grid) {
	int i, j;
	
	printf("__");
	for (i = 0 ; i < gridSize ; i++) {
		printf("_");
	}
	printf("\n");
	
	for (i = 0 ; i < gridSize ; i++) {
		printf("|");
		for (j = 0 ; j < gridSize ; j++) {
			if(grid[i][j] == 1) {
				printf("X");
			}
			else {
				printf(" ");
			}
		}
		printf("|");
		printf("\n");
	}
	printf("--");
	for (i = 0 ; i < gridSize ; i++) {
		printf("-");
	}
	printf("\n");
}

//0,0 - 0,1 - 0,2 - 0,3 - 1,0...
//checks if wrap is needed in checking for neighbours
int checkPosition(int x, int y, int size) {
	int xIn, yIn;
	int xDe, yDe;
	
	//TopLeft Corner
	if (x == 0 && y == 0) {
		xIn = 1;
		yIn = 1;
		xDe = size - 1;
		yDe = size - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//TopRight Corner
	else if (x == 0 && y == size - 1) {
		xIn = 1;
		yIn = - (size - 1);
		xDe = size - 1;
		yDe = - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//BottomLeft Corner
	else if (x == size - 1 && y == 0) {
		xIn = - (size - 1);
		yIn = 1;
		xDe = - 1;
		yDe = size - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//BottomRight Corner
	else if (x == size - 1 && y == size - 1) {
		xIn = - (size - 1);
		yIn = - (size - 1);
		xDe = - 1;
		yDe = - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//Left Side
	else if (y == 0) {
		xIn = 1;
		yIn = 1;
		xDe = - 1;
		yDe = size - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//Right Side
	else if (y == size - 1) {
		xIn = 1;
		yIn = - (size - 1);
		xDe = - 1;
		yDe = - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//Top Side
	else if (x == 0) {
		xIn = 1;
		yIn = 1;
		xDe = size - 1;
		yDe = - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	//Bottom Side 
	else if (x == size - 1) {
		xIn = - (size - 1);
		yIn = 1;
		xDe = - 1;
		yDe = - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	else {
		xIn = 1;
		yIn = 1;
		xDe = - 1;
		yDe = - 1;
		return countNeighbours(xIn, yIn, xDe, yDe, x, y);
	}
	
	return 0;
}



//count the number of neighbours
int countNeighbours(int xUp, int yUp, int xDn, int yDn, int x, int y) {
	int count = 0;
	
	if (grid[x][y + yDn] == 1) {
		count++;
	}
	if (grid[x + xUp][y + yDn] == 1) {
		count++;
	}
	if (grid[x + xUp][y] == 1) {
		count++;
	}
	if (grid[x + xUp][y + yUp] == 1) {
		count++;
	}
	if (grid[x][y + yUp] == 1) {
		count++;
	}
	if (grid[x + xDn][y + yUp] == 1) {
		count++;
	}
	if (grid[x + xDn][y] == 1) {
		count++;
	}
	if (grid[x + xDn][y + yDn] == 1) {
		count++;
	}
	
	//printf("%d \n", count);
	return count;
}

//calculates whether next generation of cell will be alive or dead
void nextGen(int x, int y, int neighbours){

	if (grid[x][y] == 1) {
		if (neighbours < 2 || neighbours > 3) {
			grid2[x][y] = 0;
		}
		else {
			grid2[x][y] = 1;
		}
	}
	else {
		if (neighbours == 3) {
			grid2[x][y] = 1;
		}
		else {
			grid2[x][y] = 0;
		}
	}
	
}



