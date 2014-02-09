#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"
#include "queue.h"
#include "task.h"


unsigned int time = 0;
queue_node * TaskRuning = NULL;
volatile int interrupt = 0;

void checkArrival(linkedlist* l, queue * q, unsigned int * taskArrived)
{
	node * listnode = l -> head; 
	while(listnode != NULL){
		if(listnode -> value -> arrival == time)
		{
			add_to_queue(listnode -> value, q);
			(*taskArrived) ++;
			printf("%d: Adding task %s\n", time, listnode -> value -> name);
		}
		listnode  = listnode -> next;
	}
}



void execute(queue * q, unsigned int * pslice, unsigned int slice//use quantum better here)
{
	if(q -> head != NULL)
	{
		// first check if finished
		if (q -> head -> value-> remaining == 0)
		{	//finshed
			printf("%d: Finished task %s\n", time, q -> head -> value -> name);
			//head changed here
			remove_from_queue(q);

			// next slice begins
			*pslice = slice; 
		} 
		//check if next head is NULL
		if (q -> head != NULL)
		{	//time slice done for this task
			if (*pslice == 0)
			{	//
				task * head = q -> head -> value;
				printf("%d: Time slice done for task %s\n", time, q -> head -> value -> name);
				remove_from_queue(q);
				add_to_queue(head, q);
				*pslice = slice;
				//head changed here
				printf("%d: Running task %s\n", time, q -> head -> value -> name);	
			}else 
			// all finished tasks removed now
			// so only care about the head(updated)
			//start a new tast as soon as possible or go on another slice
				if (q -> head  -> value -> remaining == q -> head -> value -> time || *pslice == slice)
					printf("%d: Running task %s\n", time, q -> head -> value -> name);
			

			//anyway time passed for one unit
			//wait till the end the method to do so
		  	q -> head  -> value -> remaining --;
		  	(*pslice) --;
		}
	}
}


int getTaskNum(linkedlist * l)
{
	int num = 0;
	node * listnode = l -> head;
	while(listnode != NULL){
		num ++;
		listnode = listnode -> next;
	}
	return num;
}




void roundrobin(linkedlist* l, unsigned int quantum) {

  	unsigned int taskNum = getTaskNum(l);
	unsigned int taskArrived = 0;
	//use pointer this way so that we can the 
	// variable in main function 
	unsigned int *pTaskArrived = & taskArrived;
	queue * q = (queue *)malloc(sizeof(queue));
	// the value of quantum
	unsigned int slice = quantum;
	// the addr of slice/copy of quantum, use slice to reflect the current usage of time slice
	unsigned int *pslice = & slice;
	// confusing parameter name, should use quantum for the execute method
	// all tasks arrived and the queue is empty
	while(taskArrived < taskNum || q -> head != NULL)
	{
		
		checkArrival(l,q,pTaskArrived);
		execute(q,pslice,quantum);
		time++;
	}
	printf("%d: All tasks finished\n",time-1);

}




