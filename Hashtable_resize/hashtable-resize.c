

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <assert.h>

#include "hashtable.h"



 int main(){
	hashtable *h1 = malloc(sizeof(hashtable));
	h1->capacity = 100;
 	h1->list = malloc(sizeof(node*)*100);
	//printf("%d\n", put("dog", h1));
    assert(put("dog",h1) == 1);
	//printf("%d\n", put("cat", h1));
    assert(put("cat",h1) == 1);
	//printf("%d\n", resize(h1, 200));
    assert(resize(h1,200)==1);
	//for debug
	//printf("%d\n", h1->capacity);
	//printf("%d\n", get("dog", h1));
    assert(get("dog",h1)==1);
	//printf("%d\n", get("cat", h1));
    assert(get("cat",h1)==1);
	//printf("%d\n", get("cow", h1));
    assert(get("cow",h1)==0);
	//printf("%d\n", put("cow", h1));
    assert(put("cow",h1)==1);
	//printf("%d\n", get("cow", h1));
    assert(get("cow",h1)==1);
	//free the hashtable
	delete(h1);
	free(h1);
	}


