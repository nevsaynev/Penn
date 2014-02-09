#include <stdlib.h>
#include "linkedlist.h"

int add_to_front(task* value, linkedlist* l) {
  if (value == NULL || l == NULL) return 0;

  node *new = (node *)malloc(sizeof(node));
  if (new == NULL) return 0;

  new->value = value;
  new->next = l->head;

  l->head = new;

  return 1;
}

int add_to_tail(task* value, linkedlist* l) {
  if (value == NULL || l == NULL) return 0;
  if (l->head == NULL) return add_to_front(value, l);

  node *new = (node *)malloc(sizeof(node));
  if (new == NULL) return 0;

  new->value = value;
  new->next = NULL;

  node *n = l->head;
  while (n->next != NULL) {
    n = n->next;
  }
  n->next = new;
 
  return 1;
}

int remove_from_front(linkedlist* l) {
  if (l == NULL || l->head == NULL) return 0;
  node *old = l->head;
  l->head = l->head->next;
  free(old);
}

