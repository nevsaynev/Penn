#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "linkedlist.h"
#include "task.h"

void roundrobin(linkedlist*, unsigned int);

main() {

  task *t0 = (task*)malloc(sizeof(task));
  t0->name = "bert";
  t0->arrival = 2;
  t0->time = 11;
  t0->remaining = 11;

  task *t1 = (task*)malloc(sizeof(task));
  t1->name = "ernie";
  t1->arrival = 0;
  t1->time = 8;
  t1->remaining = 8;
   
  task *t2 = (task*)malloc(sizeof(task));
  t2->name = "oscar";
  t2->arrival = 12;
  t2->time = 20;
  t2->remaining = 20;

  task *t3 = (task*)malloc(sizeof(task));
  t3->name = "grover";
  t3->arrival = 7;
  t3->time = 15;
  t3->remaining = 15;

  task *t4 = (task*)malloc(sizeof(task));
  t4->name = "elmo";
  t4->arrival = 10;
  t4->time = 4;
  t4->remaining = 4;

  linkedlist *l = (linkedlist*)malloc(sizeof(linkedlist));
  add_to_tail(t0, l);
  add_to_tail(t1, l);
  add_to_tail(t2, l);
  add_to_tail(t3, l);
  add_to_tail(t4, l);


  roundrobin(l,8);

}
