#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hashtable.h"



/*
 * For the given string refered to by the pointer "string", 
 * calculate the hashcode and update the int "value".
 * 
 * Return 1 if successful, and 0 if an error occurred 
 * (e.g. if either argument is null).
 */

node* head;

int hash(char *string, unsigned long *value)
{ 
  unsigned long hashcode = 0;

  //check if either of the argument is NULL pointer
  if (string == NULL || value == NULL) return 0;
  for (int i = 0; i < strlen(string); i++)
  {
      int charValue = string[i];
      //add up the ASCII value of each char in the string 
      hashcode = hashcode + charValue;
  }
  *value = hashcode; // why update the int value ? why not return hashcode directly

  //printf("hashcode is %ld\n", hashcode);
  //printf("value is %ld\n", *value);

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
  unsigned long hashcode = 0;

	//check if input string is null
	if (string == NULL) 
	{
	   fprintf(stderr, "ERROR: user trying to enter a null string\n");
	   return 0;
	}

   //calculate hashcode, return 0 or 1 to indicate success/failure
   if (hash(string, &hashcode) == 0) 
   {
   	   fprintf(stderr, "ERROR: fail to calculate the hashcode\n");
   	   return 0;
   }
   // determine which bucket to go into, bucket declared as global var
   int bucket = hashcode % CAPACITY; //CAPACITY defined in hashtable.h
   //printf("put bucket is %d\n", bucket);

   // Before adding to the hash table, use get function to check if the string already exists 
   if (get(string, h) == 1) return 0; //string already in the hashtable
   
   //get the head of the linked list
   node* head = h -> list[bucket];

   //create a new node to add in the hash table
   node* new = malloc(sizeof(node));

   if (new == NULL) return 0; 

   //populate the value field of the node "new"
   new -> value = malloc(sizeof(char) * (strlen(string) + 1));
   strcpy(new -> value, string);
   //put it in the front of the list
   new -> next = head;
   h -> list[bucket] = new; // why not use head = new ?
   //free(head);

   return 1;
}

/*
 * Determine whether the specified string is in the hashtable.
 * Return 1 if it is found, 0 if it is not (or if it is null).
 */
int get(char *string, hashtable *h)
{
  unsigned long hashcode = 0;

	if (string == NULL) return 0;

  // get the hashvalue
  
    if (hash(string, &hashcode) == 0) return 0;
  
  // compute the bucket
  int bucket = hashcode % CAPACITY;
  //printf("get bucket is %d\n", bucket);

  // get the head of the linked list
  node *head = h->list[bucket];

  while (head != NULL)
    {
      if (strcmp(head->value, string) == 0) return 1;
      else head = head->next;
    }
  return 0; // not found!
  
}
  


