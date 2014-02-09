I chose the second strategy, which is: 
start timing; repeat X times { randomize; sort } stop timing.
I used two methods to implement it: innerTimer & outterTimer.
innerTimer did { randomize; sort }.
outterTimer did the rest and return the elapsed time.
I also created a help method to record X times {randomize}.
and then subtract it from what outterTimer returned.
X is 1000 here as a large amount of times
I used two sets of arrays and the results are:
Set1 : N =100
		          N		    1/2N		1/4N	
bubbleSort	    30805		4328		1402
insertionSort	7113		1057		510
selectionSort	12010		2895		1031
quickSort	    18826		3135		1393

Set2 : N = 1000

				  N		    1/2N		1/4N	
bubbleSort		985909		279656		85338
insertionSort	160907		43958		11860
selectionSort	477620		131080		40092
quickSort	 	80762		30554		12615




Estimate time: 2h
Actual time: 4h