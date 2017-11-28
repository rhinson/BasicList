package edu.gcccd.csis;

import java.io.*;
import java.util.Iterator;
import java.util.Random;

/**
 * Use the starter code, including the NodeList class, our implementation of a BasicList.
 * <p>
 * We are going to use a very simple lists to store positive long numbers, one list element per digit.
 * The most significant digit is stored in the head element, the least significant digit is stored in the tail.
 * <p>
 * The starter code's main method creates very long numbers.
 * It is your task, to complete the class so that it can calculate the sum of positive very long numbers and
 * store the result in a file.
 * <p>
 * Of course, all methods need to have unit-tests to verify corner cases and happy-paths.
 * For that you may find the java.math.BigInteger class help-full when writing the unit-tests.
 * In the test code you are free to use java classes from all packages.
 * In the implementation of the Project2 class however, you are limited to
 * <p>
 * import java.io.*;
 * import java.util.Iterator;
 * import java.util.Random;
 * Moreover, you need to provide a detailed estimate for how often on average ANY iterator's next() method gets called
 * (depending on the value of L) when addition(Iterator&lt;NodeList&lt;Integer&gt;&gt; iterator) gets called.
 */
public class Project2 {

    static NodeList<Integer> generateNumber(final int maxLength) {
        final NodeList<Integer> nodeList = new NodeList<>();
        final int len = 1 + new Random().nextInt(maxLength);
        for (int i = 0; i < len; i++) {
            nodeList.append(new Random().nextInt(10));
        }
        System.out.print("Generated Number: ");
        print(nodeList);
        return nodeList;
    }

    /**
     * Prints a very long number to System.out
     *
     * @param nodeList NodeList<Integer>
     */
    static void print(final NodeList<Integer> nodeList) {
        for (final Integer i : nodeList) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void main(final String[] args) {
        final int L = 30;

        final NodeList<Integer> n1 = generateNumber(L); // (head 1st) e.g. 3457
        final NodeList<Integer> n2 = generateNumber(L); // (head 1st) e.g. 682

        final Project2 project = new Project2();

        print(project.addition(n1, n2)); //  n1+n2, e.g. 4139

        final NodeList<NodeList<Integer>> listOfLists = new NodeList<>();
        for (int i = 0; i < L; i++) {
            listOfLists.append(generateNumber(L));
        }

        Node.resetCounter();
        project.save(project.addition(listOfLists.iterator()), "result.bin");
        System.out.println("My next counter is: " + Node.getCounter());
        print(project.load("result.bin"));
    }

    /**
     * Add two very long numbers
     *
     * @param nodeList1 NodeList&lt;Integer&gt;
     * @param nodeList2 NodeList&lt;Integer&gt;
     * @return nodeList representing the sum (add) of nodeList1 and nodeList2, without leading '0'
     */
    public NodeList<Integer> addition(NodeList<Integer> nodeList1, NodeList<Integer> nodeList2) {
        NodeList<Integer> myNodeList1 = reverseList(nodeList1.iterator());
        NodeList<Integer> myNodeList2 = reverseList(nodeList2.iterator());

        NodeList<Integer> result = new NodeList<>();

        int carryOver = 0;
        int sum;
        int removeThis;

        while (myNodeList1.iterator().hasNext() || myNodeList2.iterator().hasNext()) {

            sum = carryOver;

            if (myNodeList1.iterator().hasNext()) {
                    removeThis = myNodeList1.iterator().next();
                    sum = sum + removeThis;
                    myNodeList1.remove(removeThis);
            }

            if (myNodeList2.iterator().hasNext()) {
                    removeThis = myNodeList2.iterator().next();
                    sum = sum + removeThis;
                    myNodeList2.remove(removeThis);
            }

            carryOver = sum/10;
            sum = sum%10;

            result.append(sum);
        }

        if (carryOver == 1) {
            result.append(carryOver);
        }

        if (result.iterator().hasNext()) {
            result = reverseList(result.iterator());
        } else {
            result.append(0);
        }

        while (result.iterator().next() == 0 && result.getLength() != 1) {  // Removing leading 0's but not a single 0
            result.remove(0);
        }

        return result;
    }

    /**
     * Add very long numbers
     *
     * @param iterator NodeList&lt;Integer&gt;
     * @return nodeList representing the sum (add) of all very long numbers, without leading '0'
     */
    public NodeList<Integer> addition(Iterator<NodeList<Integer>> iterator) {
        if (!iterator.hasNext()) {
            return new NodeList<Integer>();
        }

        NodeList<Integer> result = iterator.next();

        while (iterator.hasNext()) {
            NodeList<Integer> nodeList2 = iterator.next();
            result = addition(result,nodeList2);
        }

        return result;
    }

    /**
     * Saves a very large number as a file
     *
     * @param nodeList NodeList&lt;Integer&gt;
     * @param fileName String
     */
    public void save(NodeList<Integer> nodeList, String fileName) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));
            for (Integer i : nodeList) {
                try {
                    dos.writeInt(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a very large number from a file
     *
     * @param fileName String
     * @return NodeList&lt;Integer&gt;
     */
    public NodeList<Integer> load(final String fileName) {
        DataInputStream dis = null;
        NodeList<Integer> result = new NodeList<>();

        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(fileName))));
            try {
                while (dis.available() > 0) {
                    int i = dis.readInt();
                    result.append(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static NodeList<Integer> reverseList (Iterator<Integer> iter) {
        if (!iter.hasNext()) {
            return new NodeList<Integer>();
        }

        int i = iter.next();
        NodeList<Integer> reversed = reverseList(iter);
        reversed.append(i);

        return reversed;
    }
}