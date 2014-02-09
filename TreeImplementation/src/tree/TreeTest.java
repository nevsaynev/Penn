package tree;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;


import org.junit.Before;
import org.junit.Test;

public class TreeTest {
	//All the branches
	private Tree<String> c31;
	private Tree<String> c32;
	private Tree<String> c21;
	private Tree<String> c22;
	private Tree<String> c23;
	private Tree<String> c11;
	private Tree<String> c12;
	// The root of all the branches
	private Tree<String> root;
	/**
	 * edge cases
	 */
	// A single node tree 
	private Tree<String> singleNode;
	//node value cannot be null 
	private Tree<String> nullValue;
	
	@Before
	public void setUp() throws Exception {
		c31 = new Tree<String>("Seven");
		c32 = new Tree<String>("Eight");
		c23 = new Tree<String>("Six", c32);
		c22 = new Tree<String>("Five", c31);
		c21 = new Tree<String>("Four");
		c11 = new Tree<String>("Two", c21, c22);
		c12 = new Tree<String>("Three", c22, c23);
		root = new Tree<String>("One", c11, c12);
		
		singleNode = new Tree<String>("Zero");
				
		
	}
	
	@Test
	public void testTree() {
		nullValue = new Tree<String>(null);
		assertEquals(null,nullValue.getValue());
	}
	@Test
	public void testGetValue() {
		assertEquals("One", root.getValue());
		assertEquals("Two", c11.getValue());
		assertEquals("Five", c22.getValue());
		assertEquals("Seven", c31.getValue());
		
		assertEquals("Zero", singleNode.getValue());


	}

	@Test
	public void testFirstChild() {
		assertEquals(c11, root.firstChild());
		assertEquals(c22, c12.firstChild());
		assertEquals(c21, c11.firstChild());
		assertEquals(c31, c22.firstChild());
		// if this node has no child
		assertEquals(null, c21.firstChild());
		assertEquals(null, singleNode.firstChild());

	}

	@Test
	public void testLastChild() {
		// assertEquals(c12, root.lastChild());
		assertEquals(c23, c12.lastChild());
		assertEquals(c22, c11.lastChild());
		assertEquals(c31, c22.lastChild());
		// if this node has no child
		assertEquals(null, c21.lastChild());
		assertEquals(null, singleNode.lastChild());

	}

	@Test
	public void testNumberOfChildren() {
		assertEquals(2, root.numberOfChildren());
		assertEquals(2, c12.numberOfChildren());
		assertEquals(2, c11.numberOfChildren());
		assertEquals(1, c22.numberOfChildren());
		assertEquals(1, c23.numberOfChildren());
		assertEquals(0, c31.numberOfChildren());
		assertEquals(0, c32.numberOfChildren());
		assertEquals(0, singleNode.numberOfChildren());


	}

	@Test
	public void testChild() {
		assertEquals(c11, root.child(0));
		assertEquals(c12, root.child(1));
		assertEquals(c21, c11.child(0));
		assertEquals(c22, c11.child(1));
		assertEquals(c22, c12.child(0));
		assertEquals(c23, c12.child(1));


	}

	@Test(expected = NoSuchElementException.class)
	public void testChildWhenNoSuchChild() {
		// index out of range
		root.child(2);
		c12.child(-1);
		// this node has no child
		c21.child(0);
		c31.child(0);
		singleNode.child(0);

	}

	@Test
	public void testChildren() {
		Iterator<Tree<String>> itr = root.children();
		assertTrue(itr.hasNext());
		assertEquals(c11, itr.next());
		assertTrue(itr.hasNext());
		assertEquals(c12,itr.next());
		itr.remove();
		assertEquals(c11,root.lastChild());
		assertFalse(itr.hasNext());
		
		Iterator<Tree<String>> itr1 = c31.children();
		assertFalse(itr1.hasNext());
		
		Iterator<Tree<String>> itr2 = singleNode.children();
		assertFalse(itr2.hasNext());
		
		

	}

	@Test
	public void testIsLeaf() {
		assertTrue(c31.isLeaf());
		assertTrue(c32.isLeaf());
		assertFalse(c22.isLeaf());
		assertFalse(c23.isLeaf());
		assertTrue(singleNode.isLeaf());
		

	}

	@Test
	public void testEqualsObject() {
		// common equal
		Tree<String> tempc31 = new Tree<String>("Seven");
		Tree<String> tempc22 = new Tree<String>("Five", tempc31);
		assertEquals(tempc22, c22);
		// if the leaf is identical
		Tree<String> tempc23 = new Tree<String>("Six", c32);
		assertEquals(tempc23, c23);
		// if not equal
		Tree<String> tempc21 = new Tree<String>("Four");
		Tree<String> tempc12 = new Tree<String>("Three", tempc21, tempc22,
				tempc23);
		assertNotEquals(tempc12, c12);
		// if single node tree
		Tree<String> tempSingle = new Tree<String>("Zero");
		assertEquals(tempSingle, singleNode);

	}

	@Test
	public void testToString() {
		assertEquals("Three\n  Five\n    Seven\n  Six\n    Eight\n",
				c12.toString());
		assertEquals("Five\n  Seven\n",
				c22.toString());
		assertEquals("Zero\n", singleNode.toString());
		
	}
	
	@Test
	public void testSetValue() {
		Tree<String> test = new Tree<String>("");
		test.setValue("Test");
		assertEquals("Test", test.getValue());
		test.setValue("lemontree");
		assertEquals("lemontree", test.getValue());

	}

	@Test
	public void testAddChildTreeOfV() {
		Tree<String> c41 = new Tree<String>("Eleven");
		Tree<String> c42 = new Tree<String>("Twlve");
		c31.addChild(c41);
		c31.addChild(c42);
		assertEquals(c41, c31.firstChild());
		assertEquals(c42, c31.child(1));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddChildTreeOfVWhenTreeIsInChild() {
		c11.addChild(root);
		c22.addChild(c11);
	}

	@Test
	public void testAddChildIntTreeOfV() {
		Tree<String> c33 = new Tree<String>("Nine");
		Tree<String> c34 = new Tree<String>("Ten");
		c23.addChild(0, c33);
		c23.addChild(0, c34);
		assertEquals(c34, c23.firstChild());
		assertEquals(c34, c23.child(0));
		assertEquals(c33, c23.child(1));
		assertEquals(c32, c23.child(2));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddChildIntTreeOfVWhenTreeIsInChild() {
		c11.addChild(1, root);
		c23.addChild(2, c12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddChildIntTreeOfVWhenIndexOutOfRange() {
		c11.addChild(5, root);
		c23.addChild(-1, c31);
	}

	@Test
	public void testAddChildren() {
		// add two children to c11
		c11.addChildren(new Tree<String>("Banana"), new Tree<String>("Minion"));
		assertEquals("Banana", c11.child(2).getValue());
		assertEquals("Minion", c11.lastChild().getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddChildrenWhenTreeIsInvaild() {
		c11.addChildren(root,c23);
	}

	@Test
	public void testRemoveChild() {

		// check if the removed subtree is intact
		assertEquals("Three\n  Five\n    Seven\n  Six\n    Eight\n",
				root.removeChild(1).toString());
		// check the original tree
		assertEquals(c11, root.firstChild());
		assertEquals(c11, root.lastChild());
		assertEquals(
				"One\n  Two\n    Four\n    Five\n      Seven\n",
				root.toString());
		
		

	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveChildWhenIndexOutOfRange() {
		c23.removeChild(2);
		c31.removeChild(0);
	}
}
