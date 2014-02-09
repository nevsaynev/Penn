#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#import "linkedlist.h"
#import "linkedlist.c"

int main()
{
	//node* new = malloc(sizeof(node));
    linkedlist* list = malloc(sizeof(list));

	int m = add_to_front("dog", list);
	node *hi = list->head;
	printf("add %s to the linkedlist\n", hi -> word);
	assert(m == 0);

    
	int n = add_to_front("dog", list);
	assert(n == 0);

	int k = add_to_front("cat", list);
	assert(k == 0);

	int x = find("dog", list);
	assert(x == 1);

    int y = find("cat", list);
    assert(y == 1);

    int z = find("mom", list);
    printf("%d", z);
    assert(z == 0);

	return 0;
}