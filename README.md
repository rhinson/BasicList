# BasicList
Use the starter code, including the NodeList class, our implementation of a BasicList.
We are going to use a very simple lists to store positive long numbers, one list element per digit. The most significant digit is stored in the head element, the least significant digit is stored in the tail.

The starter code's main method creates very long numbers. It is your task, to complete the class so that it can calculate the sum of positive very long numbers and store the result in a file.

Of course, all methods need to have unit-tests to verify corner cases and happy-paths. For that you may find the java.math.BigInteger class help-full when writing the unit-tests. In the test code you are free to use java classes from all packages. In the implementation of the Project2 class however, you are limited to

* import java.io.*; 
* import java.util.Iterator; 
* import java.util.Random; 

Moreover, you need to provide a detailed estimate for how often on average ANY iterator's next() method gets called (depending on the value of L) when addition(Iterator<NodeList<Integer>> iterator) gets called.

# Big O Notation

In my code, iterator next() is called one time per generated number within the addition(Iterator<NodeList<Integer>> iterator) method.
When the addition(Iterator<NodeList> iteratator) calls the addition(NodeList<Integer> nodeList1, NodeList<Integer> nodeList2) method,
the iterator next() method is called up to twice per digit when summing the nodeList inputs, up to sixty times total.
The addition(NodeList<Integer> nodeList1, NodeList<Integer> nodeList2) method also calls the reverseList(Iterator<Integer> iter) method up to three times 
per iteration.
The reverse(Iterator<Integer> iter) method calls iterator next() up to thirty times per call.

In all the iterator next() is called up to 90 times per pair of nodeLists entered.

The number of calls is a linear growth based on the number of nodes in each nodeList and number of summed nodeLists
resulting in an O(n) designation. 

# Correction

For a Nodelist of a given length, when the number of nodelists increases, the number of calls to next() is constant, but
when the length of the nodelist grows with the number of nodelists, the growth of calls to next() is exponential. 




