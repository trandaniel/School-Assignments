#include <stdio.h>
#include "SumSquares.h"
int main (void) {

  printf("SumSquares(2:4) is:   %10d\n",SumSquares(2,4));
  printf("SumSquares(0:5) is:   %10d\n",SumSquares(0,5));
  printf("SumSquares(3:3) is:   %10d\n",SumSquares(3,3));
  printf("SumSquares(3:4) is:   %10d\n",SumSquares(3,4));
  printf("SumSquares(0:0) is:   %10d\n",SumSquares(0,0));
  printf("SumSquares(0:100) is: %10d\n",SumSquares(0,100));
  printf("SumSquares(-2:2) is:  %10d\n",SumSquares(-2,2));
}
