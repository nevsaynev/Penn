#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"


FILE *infile;

int main(int argc, char* argv[]) 
{
  if (argc < 3) return 1;

  char* programName = argv[0];
  char* firstFile = argv[1];
  char* secondFile = argv[2];
  
  linkedlist* list = malloc(sizeof(linkedlist));

  if(file_initialize(firstFile) == 1) 
  {
  	while (!feof(infile) && !ferror(infile))
    {
  	  char* word = file_read_next();

  //check if the word already in the linked list, if not, add to the linked list
  	  if (word != NULL && find(word, list) == 0) 
      {
        add_to_front(word, list);
      }
  	  //printf("%s\n", word);
    } 
  }

  
  //create the second linked list 
  linkedlist* list1 = malloc(sizeof(linkedlist));
  int count = 0;
  
  int open = file_initialize(secondFile);
  
  if(open== 1) 
  {
  	while (!feof(infile) && !ferror(infile))
    {
  	  char* word1 = file_read_next(); //read one word from the file 
     
  	  if (word1 != NULL && find(word1, list1) == 0) 
      {      
        add_to_front(word1, list1);
        //printf("%s", word1);
      }
   
    }

    //printf("All the words in the file are: %s\n", word1);

    //iterate one linked list and check if each word is also in the other list
    node* ptr = list -> head;
    while (ptr != NULL)
    {
       if(find(ptr -> word, list1) == 1) 
       {
        //printf("The common words are: %s\n", ptr -> word);
        count += 1;
       }
       ptr = ptr -> next;
    }
     printf("The total number of words in common is %d\n", count);
  }

  fclose(infile);

  return 0;

}

/*
 * Check if a file has been successfully opened.
 * Return 1 if successfully opened a file, 0 if not (or if it is null).
 */
int file_initialize(char* name)
{
  if (name == NULL) {
    printf("Error! File name provided to file_initialize is null\n");
    return 0;
  }
  infile = fopen(name, "r");
  if (infile == NULL) {
    printf("Error! Could not open file \"%s\" for reading\n", name);
    return 0;
  }
  else return 1;
}

/*
 * Read in one word at a time from a file
 * Return the word read.
 */
char file_word[257];
char* file_read_next()
{
  if (fscanf(infile, "%s", file_word) == EOF) return NULL;
  else return file_word;
  //printf("word: %s\n", file_word);
}