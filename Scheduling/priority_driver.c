#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"
#include "task.h"

void priority_roundrobin(linkedlist*, int);


main() {

  task *t0 = (task*)malloc(sizeof(task));
  t0->name = "sleepy";
  t0->arrival = 0;
  t0->time = 15;
  t0->remaining = 15;
  t0->priority = 1;

  task *t1 = (task*)malloc(sizeof(task));
  t1->name = "dopey";
  t1->arrival = 2;
  t1->time = 5;
  t1->remaining = 5;
  t1->priority = 9;

  task *t2 = (task*)malloc(sizeof(task));
  t2->name = "doc";
  t2->arrival = 0;
  t2->time = 6;
  t2->remaining = 6;
  t2->priority = 8;

  task *t3 = (task*)malloc(sizeof(task));
  t3->name = "grumpy";
  t3->arrival = 17;
  t3->time = 7;
  t3->remaining = 7;
  t3->priority = 5;

  task *t4 = (task*)malloc(sizeof(task));
  t4->name = "happy";
  t4->arrival = 8;
  t4->time = 3;
  t4->remaining = 3;
  t4->priority = 8;

  task *t5 = (task*)malloc(sizeof(task));
  t5->name = "bashful";
  t5->arrival = 16;
  t5->time = 9;
  t5->remaining = 9;
  t5->priority = 4;

  task *t6 = (task*)malloc(sizeof(task));
  t6->name = "sneezy";
  t6->arrival = 17;
  t6->time = 5;
  t6->remaining = 5;
  t6->priority = 4;


  linkedlist *l = (linkedlist*)malloc(sizeof(linkedlist));
  add_to_tail(t0, l);
  add_to_tail(t1, l);
  add_to_tail(t2, l);
  add_to_tail(t3, l);
  add_to_tail(t4, l);
  add_to_tail(t5, l);
  add_to_tail(t6, l);


  priority_roundrobin(l, 4);

}



