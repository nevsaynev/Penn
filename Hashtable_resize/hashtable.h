
//
//  hashtable.h
//  HW2
//
//  Created by Dong Yan on 1/20/14.
//  Copyright (c) 2014 Dong Yan. All rights reserved.
//



typedef struct Node node;
struct Node {
    char *value;
    node *next; // next node in the list
};



typedef struct Hashtable hashtable;
struct Hashtable {
   	node **list;
   	//node *list[]; // "buckets" of linked lists
   	unsigned int capacity;
};

/* Function prototypes */
int hash(char *, unsigned long *);
int put(char *, hashtable *);
int get(char *, hashtable *);
int resize(hashtable *, unsigned int);
int delete(hashtable *);
