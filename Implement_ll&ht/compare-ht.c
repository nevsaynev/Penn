#include <stdio.h>
#include <stdlib.h>
#include "hashtable.h"

FILE *infile;

// fail return 1, succeed return 0
int main(int argc, char* argv[]) 
{
  if (argc < 3) return 1;

  char* programName = argv[0]; //when you run, the commond is ./compare-ht anna.txt war.txt
  char* firstFile = argv[1];
  char* secondFile = argv[2];

  hashtable* h = malloc(sizeof(hashtable));

  int open = file_initialize(firstFile);

  if(open == 1) 
  {
  	while (!feof(infile) && !ferror(infile))
    {
  	  char* word = file_read_next();
      if (word != NULL && get(word, h) == 0) {
  	     put(word, h);
      }
  	//printf("%s\n", word);
    }
  }

  hashtable* h1 = malloc(sizeof(hashtable));
  int count = 0;

  if(file_initialize(secondFile) == 1) 
  {

  	while (!feof(infile) && !ferror(infile))
    {
  	   char* word1 = file_read_next(); //read one word from the file 
  	
       if (word1 != NULL && get(word1, h1) == 0) 
       { 
          //if word is found in the first hashtable, count += 1
          if (get(word1, h) == 1) count += 1;
          //printf("%s\n", word1);
          put(word1, h1);
       }
    }

  }
  
  printf("The total number of words in common are %d\n",count);

  fclose(infile);
  
  return 0;

}


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


char file_word[257];
char* file_read_next()
{
  if (fscanf(infile, "%s", file_word) == EOF) return NULL;
  else return file_word;
  //printf("word: %s\n", file_word);
}