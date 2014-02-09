#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"
#include "queue.h"
#include "task.h"



// the time axis
unsigned int time = 0;
// a node to identify which task is running
queue_node * TaskRuning = NULL;
//stil interrupt
//??? why again
volatile int interrupt = 0;


void checkArrival(linkedlist* l, queue * q, unsigned int * taskArrived)
{
	node * listnode = l -> head; 
	while(listnode != NULL){
		if(listnode -> value -> arrival == time)
		{
			add_to_queue_priority(listnode -> value, q);
			(*taskArrived) ++;
			printf("%d: Adding task %s\n", time, listnode -> value -> name);
		}
		listnode  = listnode -> next;
	}
}

void execute(queue * q, unsigned int * pslice, unsigned int slice)
{	//always check if queue is empty first
	if(q -> head != NULL)
	{	// put the task in head to the runningtask position if it's not there
		if (TaskRuning != q -> head) printf("%d: Running task %s\n", time, q -> head -> value -> name);	
		TaskRuning = q -> head;
		// deal with the left over from last execution
		if (TaskRuning -> value-> remaining == 0)
		{	
			printf("%d: Finished task %s\n", time, TaskRuning -> value -> name);
			remove_from_queue(q);
			TaskRuning = NULL;
			//start the new slice immediately
			*pslice = slice; 
		} 
		// real execution starts here
		if (q -> head != NULL)
		{
			if (*pslice == 0)
			{	//if not finished but time slice used up
				task * head = q -> head -> value;
				printf("%d: Time slice done for task %s\n", time, q -> head -> value -> name);
				remove_from_queue(q);
				add_to_queue_priority(head, q);
				// continue running this task
				if(TaskRuning == q -> head){
					interrupt =0;
					printf("%d: Running task %s\n", time, q -> head -> value -> name);	
				} 
				// and start a new time slice
				*pslice = slice;
			}
			// if round robin to another task(with the same priority) 
			if (TaskRuning != q -> head)
				printf("%d: Running task %s\n", time, q -> head -> value -> name);
				//update the taskrunning position	
			TaskRuning = q -> head;
			// always time minus one 
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



void interruptRespond(queue * q, unsigned int * pslice,unsigned int slice)
{
	if(!interrupt || TaskRuning == NULL???) {
		interrupt = 0;
		return;
	}
	printf("%d: Pre-empting task %s\n", time, TaskRuning -> value -> name);
	(*pslice) = slice;
	interrupt = 0;
}

void priority_roundrobin(linkedlist* l, unsigned int quantum) {

  	unsigned int taskNum = getTaskNum(l);
	unsigned int taskArrived = 0;
	unsigned int *pTaskArrived = & taskArrived;
	queue * q = (queue *)malloc(sizeof(queue));
	unsigned int slice = quantum;
	unsigned int *pslice = & slice;
	while(taskArrived < taskNum || q -> head != NULL)
	{
		
		checkArrival(l,q,pTaskArrived);
		interruptRespond(q,pslice,quantum);
		execute(q,pslice,quantum);
		time++;
	}
	printf("%d: All tasks finished\n",time-1);

}


//??? so how to make logical
