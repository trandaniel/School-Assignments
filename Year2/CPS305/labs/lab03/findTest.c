#include <stdio.h>

int find (int item, int *A, int max_index);

int main (void) {

  int A[10]={4,5,2,3,1,6,9,8,0};

  printf("find(4,A,8) returned %d\n", find(4,A,8));
  printf("find(0,A,8) returned %d\n", find(0,A,8));
  printf("find(1,A,8) returned %d\n", find(1,A,8));
  printf("find(32,A,8) returned %d\n", find(32,A,8));
  return (0);
}

int find (int item, int *A, int max_index) {

  if (max_index < 0 ) return -1; //not found
  if (item==A[max_index]) return max_index;
  else return find(item,A,max_index-1);
  
}
