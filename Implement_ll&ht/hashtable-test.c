#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#import "hashtable.h"
#import "hashtable.c"

int main()
{
  hashtable* h1 = malloc(sizeof(hashtable));
  hashtable* h2 = malloc(sizeof(hashtable)); 
  //why "dog" can be directly use as argument here, it should be a pointer argument
  int x = put("dog", h1); 
  int y = put("cat", h1);

  // printf("%d\n", x);
  // printf("%d\n", y);

  assert(x == 1);
  assert(y == 1);

  int b = put("dog", h1);

  // printf("%d\n", b);

  assert(b == 0);

  int m = put("dog", h2);

  // printf("%d\n", m);

  assert(m == 1);

  int n = get("dog", h2);

  // printf("%d\n", n);

  assert(n == 1);

  int k = get("cat", h2);

  // printf("%d\n", k);

  assert(k == 0);
  
  return 0;
}