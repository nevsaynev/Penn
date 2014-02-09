#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"

// a global variable to set the interrupt pin
extern int interrupt;
/*
 * Add the string to the rear of the queue
 * 
 * Return 1 if successful, and 0 if an error occurred 
 * (e.g. if the string is null, if the queue is null,
 * or if memory could not be allocated.
 */
int add_to_queue(task* t, queue* q)
{
	if (t == NULL) return 0;

	queue_node * data;

	data = (queue_node *)malloc(sizeof(queue_node));

	if( data != NULL )
		data -> value = t;
	else
		return 0;

	if (q -> head == NULL)
	{
		q -> head = data;
		q -> tail = data;


	} else
	{
		q -> tail -> next = data; 
		q -> tail = data; 
	}
	return 1;   
}


/*
 * Remove the node from the front of the queue 
 * and copy the contents of the node to the address
 * pointed to by the string pointer.
 * Return 0 if an error occurred (e.g. if the queue
 *  is empty, or if either argument is null), 1 otherwise
 */
int remove_from_queue(queue* q)
{
	if (q == NULL || q -> head == NULL) return 0;
	
	if (q -> head == q -> tail)
	{
		free(q -> head);
		q -> head = q -> tail = NULL;
	}

	else 
	{
		queue_node * tmp = q -> head; 
		q -> head = q -> head -> next;
		free(tmp);
	}
	return 1;
}
 
  /*
 * Add the tas to the rear of the queue
 * 
 * Return 1 if successful, and 0 if an error occurred 
 * (e.g. if the string is null, if the queue is null,
 * or if memory could not be allocated.
 */
int add_to_queue_priority(task* t, queue* q)
//take care of the speacial cases(null, empty)first
{	//if the task is null return 0
	if (t == NULL) return 0;

	queue_node * data;


	data = (queue_node *)malloc(sizeof(queue_node));
//if no memory could be allocated return 0
	if( data != NULL )
		// this node 's value is task
		data -> value = t;
	else
		return 0;
	// if the queue is already empty, add the node to the front
	if (q -> head == NULL)
	{
		q -> head = data;
		q -> tail = data;


	} else
	{	// if the priority of the current node in the head of queue is no greater than 
		// the dealing task
		// set interrupt 
		// and add this task to the front of the queue again
		if (q -> head -> value -> priority < t -> priority){
			interrupt = 1;
			data -> next = q -> head;
			q -> head = data;
		}else{
			// find the right place to insert this task
			queue_node * itr = q -> head;
			while(itr -> next && itr -> next -> value -> priority > t -> priority){
				itr = itr -> next;
			}
			if (itr -> next) {
				queue_node * tmp = itr -> next;
				itr -> next = data;
				data -> next = tmp;
			}else{
				// if the next node doesn't exist
				//just add the node to the rear 
				// and reset the tail
				itr -> next = data;
				q->tail = data->next;
				//??? why don't take care of tail 
			}


		}
	}
	return 1;   
}