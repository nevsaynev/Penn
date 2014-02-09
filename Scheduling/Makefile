all: round_robin round_robin_priority

round_robin: queue linkedlist
	gcc -o round_robin linkedlist.o queue.o roundrobin.c driver.c 

round_robin_priority: queue linkedlist
	gcc -o round_robin_priority priority_driver.c priority.c linkedlist.o queue.o

queue:
	gcc -c queue.c

linkedlist:
	gcc -c linkedlist.c

clean:
	rm round_robin round_robin_priority *.o