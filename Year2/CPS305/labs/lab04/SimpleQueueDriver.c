#include <stdio.h>
#include <stdlib.h>
#include "QueueInterface.h"

int main(void) {
    Queue Q;
    int item, ret_val, i,removedItem, removedItemArray[5], insertedItemArray[5];
    InitializeQueue(&Q); printf("Q Created.\n");

    /*insert 'x' onto Q*/
    item ='x';
    ret_val=Insert(item,&Q); 
    if ( ret_val ) printf("Insert of %c succeeded.\n",item);
    else  printf("Insert of %c failed.\n",item);

    ret_val=Remove(&Q,&removedItem); 
      if ( ! ret_val ) printf("Remove failed. \n");
      else printf("Remove succeeded ");
      if ( removedItem != item ) 
           printf ("but value wrong.\n");
      else printf ("and value correct (%c).\n",removedItem);

}

