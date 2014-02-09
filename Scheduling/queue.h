#include "task.h"
typedef struct QNode queue_node;
struct QNode {
  task * value;
  queue_node *next; // next node in the list
};

typedef struct Queue queue;
struct Queue {
  queue_node *head; // point to first element
  queue_node *tail; // point to last element
};

/* Function prototypes */
int add_to_queue(task*, queue*);
int remove_from_queue(queue*);
int add_to_queue_priority(task*, queue*);
