#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "linkedlist.h"

int add_to_front(char* word, linkedlist* list) 
{
	node* new = malloc(sizeof(node));
	if(word == NULL|| new == NULL || list == NULL) return -1;
	//populate the value field of the node "new"
    new -> word = malloc(sizeof(char) * (strlen(word) + 1));
    strcpy(new -> word, word);
    //put it in the front of the list
    new -> next = list -> head;
    list -> head = new;
    return 0;
}


/*
 * Determine whether the specified word is in the linked list.
 * Return 1 if it is found, 0 if it is not (or if it is null).
 */
int find(char* word, linkedlist* list)
{
   if(list -> head == NULL || word == NULL) return 0;

   node* temp;

   for(temp = list -> head; temp != NULL; temp = temp -> next)
   {
   	  if (strcmp(temp -> word, word) == 0) return 1;
   }

   return 0;
}


