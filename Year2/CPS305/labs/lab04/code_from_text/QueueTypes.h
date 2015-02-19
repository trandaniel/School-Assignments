/*   ------------< begin file "QueueTypes.h" >------------   */

typedef  int  ItemType;           /* the ItemType can be arbitrary. */

typedef  struct  QueueNodeTag {
            ItemType               Item;   
            struct  QueueNodeTag  *Link;
         }QueueNode;
                     
typedef  struct {                                 /* a queue is empty iff */
            QueueNode  *Front;                       /* its Front == NULL */
            QueueNode  *Rear;
         }Queue;

/*   ------------< end-of-file "QueueTypes.h" >------------   */

