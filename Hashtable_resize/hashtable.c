//
//  hashtable.c
//  HW2
//
//  Created by Dong Yan on 1/21/14.
//  Copyright (c) 2014 Dong Yan. All rights reserved.
//


/*
 * Note that there is an assumption that each word in the file is less than
 * 256 characters long.
 *
 * Note that this implementation only allows you to read one file at a time.
 * To read another file, simply call file_initialize again (with the name
 * of the file to read). Or modify this code. =)
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hashtable.h"

/*
 * For the given string refered to by the pointer "string",
 * calculate the hashcode and update the int "value".
 *
 * Return 1 if successful, and 0 if an error occurred
 * (e.g. if the string is null).
 */
int hash(char *string, unsigned long *value)
{
	if ( (string) == NULL || (value) == NULL)
		return 0;
	unsigned long sum = 0;
	int i;
	for(i = 0; i< strlen(string);i++){
        sum = sum + string[i];
    }
    *value  = sum;
	return 1;
	
}


/*
 * Add the string to the hashtable in the appropriate "bucket".
 *
 * Return 1 if successful, and 0 if an error occurred
 * (e.g. if the string is null, if memory could not be allocated,
 * or if the string is already in the hashtable).
 */
int put(char *string, hashtable *h)
{
    // if string is null
	if ( (string) == NULL) return 0;
    // if already exists
    if (get(string,h)==1) return 0;
    //determine the bucket number
	unsigned long Value = 0;
	int index = -1;
    if(hash(string, &Value) == 1){
        index = (Value) % (h->capacity);
    }
    if (index == 0 )return 0;
	//add new node to the chosen bucket
	node *head =h->list[index];
	node *new = malloc(sizeof(node));
	//if could not be allocated
    if (new == NULL) return 0;
	//populate the value field
	new->value = malloc(sizeof(char) * (strlen(string) + 1));
	//if could not be allocated
	if(new->value ==NULL) return 0;
	strcpy(new->value, string);
	//put it at the front of the list
	new->next = head;
    h->list[index] = new;
	return 1;
    
}


/*
 * Determine whether the specified string is in the hashtable.
 * Return 1 if it is found, 0 if it is not (or if it is null).
 */
int get(char *string, hashtable *h)
{
	if ( (string) == NULL) return 0;
	//determine the bucket number
	unsigned long Value = 0;
	
	int index = -1;
    if(hash(string, &Value) == 1){
        index = (Value) % h->capacity;
    }
    
	//look through the linked list and starting with the head
	node *n = h->list[index];
	while (n != NULL) {
		if (strcmp(n->value, string) == 0 ) {
			return 1;
		}
		else n = n->next;
	}
	//found nothing
  	return 0 ;
}

/*
 *This function should create a new array of size "capacity" of linked lists
 *and then populate it with all of the elements of the hashtable
 *it should then point the hashtable's "list" field to that new array 
 */

 int resize(hashtable *h, unsigned int capacity)
 {
  	hashtable *new = malloc(sizeof(hashtable));
 	new->list = malloc(sizeof(node*)*capacity);
 	new->capacity = capacity;

 	
 	//populate hashtable to array of linked lists 
 	int j;
 	for(j = 0;j<h->capacity;j++){
		//look through the linked list and starting with the head
		node *n = h->list[j];
		while (n != NULL) {
			char *string = n->value;
			//for debug
			//printf("%s\n", string);
			put(string, new);
			//for debug
			//printf("%d\n", get(string,new));
			n = n->next;
		}
	}

	delete(h);
	h->list = new->list;
	h->capacity = capacity;
	//free hashtable
	free(new);
	return 1;
}

/*
 * This function is used to free the inner three layers of a hashtable
 * Return 1 if free them all
 */
int delete(hashtable *h){
	//free memory of 3 layers
	int i;
	for(i = 0; i<h->capacity;i++){
		node *n = h->list[i];
		while (n != NULL) {

			char *str = n->value;
			//free the memory to hold the string
			free(str);
			node *n1 = n;
			n = n->next;
			//free the memory to hold the node
			free(n1);
		}

	}
	//free the memory for the array
	free(h->list);
	return 1;
}

