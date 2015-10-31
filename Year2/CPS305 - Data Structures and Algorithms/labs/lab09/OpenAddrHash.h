#define M 17
#define EmptyKey -1
typedef int KeyType;
typedef char InfoType;


typedef struct {
	KeyType Key;
	InfoType Info;
	} TableEntry;

typedef TableEntry Table[M];
