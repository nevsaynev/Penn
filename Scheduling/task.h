/*
  This represents a task (process) to be scheduled.
*/
#ifndef TASK_H
#define TASK_H

typedef struct Task task;
struct Task {
  char *name;
  unsigned int arrival; // time at which this task arrives
  unsigned int time;    // time it takes to complete this task
  unsigned int remaining; // amount of time left to complete this task
  unsigned int priority;
};
#endif
