#include <stdlib.h>
#include "task.h"
/*
 This is a simple implementation of a linked list.
*/

typedef struct Node node;
struct Node {
  task *value;
  node *next;
};

typedef struct LinkedList linkedlist;
struct LinkedList {
  node *head;
};

int add_to_front(task*, linkedlist*);
int add_to_tail(task*, linkedlist*);
int remove_from_front(linkedlist*);


