package edu.gcccd.csis;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Project2Test {

    private static BigInteger genBigInteger(final NodeList<Integer> nodeList) {
        final StringBuilder sb = new StringBuilder();
        for (final int i : nodeList) {
            sb.append(i);
        }
        return new BigInteger(sb.toString());
    }

    private static NodeList<Integer> genNodeList(final String s) { // "100" .. '1','0','0'
        final NodeList<Integer> nodeList = new NodeList<>();
        for (final char c : s.toCharArray()) {
            nodeList.append(Character.getNumericValue(c)); // '0' ..'9'
        }
        return nodeList;
    }


    String myFileName = "myTestFile.bin";
    File file = new File(myFileName);
    Path myFilePath = Paths.get(myFileName);

    final Project2 project = new Project2();

    @Before
    public void tearDown() {
    try {
        Files.deleteIfExists(Paths.get(String.valueOf(myFilePath)));
        } catch (IOException e) {
        System.out.println("Failed due to: " + e.toString());
        }
    }

    // Simple test of two numbers
    @Test
    public void additionTest1() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(1);
        n2.append(2);

        NodeList<Integer> result = project.addition(n1, n2);

        assertEquals("3",result.toString());
    }

    // Test that carryOver is working
    @Test
    public void additionTest2() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(9);
        n1.append(9);
        n2.append(1);

        NodeList<Integer> result = project.addition(n1, n2);

        assertEquals("100",result.toString());
    }

    // Test that leading 0's are removed
    @Test
    public void additionTest3() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(0);
        n1.append(0);
        n1.append(0);
        n1.append(9);
        n1.append(9);
        n2.append(1);

        NodeList<Integer> result = project.addition(n1, n2);

        assertEquals("100",result.toString());
    }

    // Test proper length of nodelist and alternate comparison of returns
    @Test
    public void additionTest4() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(0);
        n1.append(0);
        n1.append(0);
        n1.append(8);
        n1.append(9);
        n2.append(2);
        n2.append(3);

        NodeList<Integer> actual = project.addition(n1, n2);

        NodeList<Integer> expected = new NodeList<>();
        expected.append(1);
        expected.append(1);
        expected.append(2);

        assertEquals(5,n1.getLength());
        assertEquals(expected.iterator().next(),actual.iterator().next());

    }

    // Test that last 0 is left
    @Test
    public void additionTest5() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(0);
        n2.append(0);

        NodeList<Integer> result = project.addition(n1, n2);

        assertEquals("0",result.toString());
    }

    // Test passing NULL n2
    @Test
    public void additionTest6() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(9);

        NodeList<Integer> result = project.addition(n1, n2);

        assertEquals("9",result.toString());
    }

    /**
     * Adding two long integer values
     */
    @Test
    public void testAddition() {

        final NodeList<Integer> n1 = project.generateNumber(30);
        final NodeList<Integer> n2 = project.generateNumber(30);

        final BigInteger N1 = genBigInteger(n1);
        final BigInteger N2 = genBigInteger(n2);

        final NodeList<Integer> n3 = project.addition(n1, n2);
        final BigInteger N3 = N1.add(N2);

        assertEquals(N3, genBigInteger(n3));
    }

    /**
     * Adding integer values corner cases
     */
    @Test
    public void testAdditionCC() {
        NodeList<Integer> n1 = genNodeList("007");
        NodeList<Integer> n2 = genNodeList("10");

        NodeList<Integer> n3 = project.addition(n1, n2);
        assertEquals(new BigInteger("17"),genBigInteger(n3));

        // no leading 0s
        assertEquals(2, n3.getLength());

        // app does not crash on empty lists ...
        n1 = new NodeList<>();
        n2 = new NodeList<>();
        n3 = project.addition(n1, n2);
        assertEquals(new BigInteger("0"), genBigInteger(n3));

        // 0 + 0 = 0
        n1 = genNodeList("0");
        n2 = genNodeList("0");
        n3 = project.addition(n1, n2);
        assertEquals(1, n3.getLength());
        assertEquals(new BigInteger("0"), genBigInteger(n3));

        // 0 + empty list  = 0
        n2 = new NodeList<>();
        n3 = project.addition(n1, n2);
        assertEquals(1, n3.getLength());
        assertEquals(new BigInteger("0"), genBigInteger(n3));

        // empty list + 0  = 0
        n3 = project.addition(n2, n1);
        assertEquals(1, n3.getLength());
        assertEquals(new BigInteger("0"), genBigInteger(n3));
    }

    /**
     * Adding several long integer values
     */
    @Test
    public void testAdditionIter() {
        // fun 12345679 * 72 = 888,888,888
        NodeList<NodeList<Integer>> list = new NodeList<>(); // 72 nodelist all repre. 12345679
        for (int i = 0; i < 72; i++) {
            list.append(genNodeList("12345679"));
        }
        NodeList<Integer> result = project.addition(list.iterator());
        assertEquals(new BigInteger("888888888"), genBigInteger(result));
    }

    @Test
    public void iteratorAdditionTest7() {

        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        final NodeList<Integer> n3 = new NodeList<>();
        final NodeList<Integer> n4 = new NodeList<>();
        final NodeList<Integer> n5 = new NodeList<>();

        n1.append(0);
        n1.append(0);
        n1.append(0);
        n1.append(8);
        n1.append(9);
        n2.append(2);
        n2.append(3);
        n3.append(1);
        n3.append(0);
        n4.append(0);
        n5.append(9);
        n5.append(9);

        final NodeList<NodeList<Integer>> listOfLists = new NodeList<>();
        listOfLists.append(n1);
        listOfLists.append(n2);
        listOfLists.append(n3);

        NodeList<Integer> actual = project.addition(listOfLists.iterator());

        NodeList<Integer> expected = new NodeList<>();
        expected.append(1);
        expected.append(3);
        expected.append(1);

        assertEquals(expected.iterator().next(),actual.iterator().next());

    }

    @Test
    public void saveTest1() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(0);
        n1.append(0);
        n1.append(0);
        n1.append(8);
        n1.append(9);
        n2.append(2);
        n2.append(3);

        assertFalse(file.exists());
        project.save(project.addition(n1,n2), myFileName);
        assertTrue(file.exists());
    }

    @Test
    public void loadTest1() {
        final NodeList<Integer> n1 = new NodeList<>();
        final NodeList<Integer> n2 = new NodeList<>();
        n1.append(0);
        n1.append(0);
        n1.append(0);
        n1.append(8);
        n1.append(9);
        n2.append(2);
        n2.append(3);

        project.save(project.addition(n1,n2), myFileName);
        assertTrue(file.exists());

        NodeList<Integer> expected = new NodeList<>();
        expected.append(1);
        expected.append(1);
        expected.append(2);

        assertEquals(expected.iterator().next(),project.load(myFileName).iterator().next());
    }

}
