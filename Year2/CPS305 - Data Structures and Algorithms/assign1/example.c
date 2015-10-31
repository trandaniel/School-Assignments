#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
int add(int num, ...);

int main(void) {
	printf("%d\n",add(3, 1, 2, 3, 4, 5, 6, 7));
}

int add(int num, ...) {
	int sum = 0.0;
	int i;
	va_list arguments;
	va_start(arguments, num);
	for (i = 0; i < 7; i++) {
		printf("%d \n", va_arg(arguments, int));
		printf("%d \n", va_arg(arguments, int));
	}
	va_end(arguments);
	return sum;
}
