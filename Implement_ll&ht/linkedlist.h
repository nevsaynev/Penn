typedef struct Node node;
struct Node {
  char *word;
  node *next; // next node in the list
};

typedef struct Linkedlist linkedlist;
struct Linkedlist {
  node* head;
};

/* Function prototypes */
int add_to_front(char* word, linkedlist* list);
int find(char* word, linkedlist* list);
int file_initialize(char* name);
char* file_read_next();