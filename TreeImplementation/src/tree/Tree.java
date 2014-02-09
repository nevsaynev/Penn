/**
 * 
 */
package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of an ADT for general trees
 * 
 * @author Dong Yan
 * 
 */

public class Tree<V> {
	// fields
	// value of the node
	private V value;
	// children of the node
	// keep in ArrayList
	private ArrayList<Tree<V>> childrenList;

	/**
	 * Constructor: Creates a Tree node with the given value and zero or more
	 * children
	 * 
	 * @param value
	 *            of the tree
	 * @param children
	 *            of the tree, in an array
	 */
	@SafeVarargs
	public Tree(V value, Tree<V>... children) {
		if (value != null) {
			this.value = value;
		}
		this.childrenList = new ArrayList<Tree<V>>();

		for (Tree<V> c : children) {
			this.childrenList.add(c);
		}
	}

	/**
	 * Returns the value in this node of the Tree
	 * 
	 * @return the value in this node
	 */
	public V getValue() {
		return this.value;
	}

	/**
	 * Returns the first child of this tree (or null if this tree is a leaf)
	 * 
	 * @return the first child of this tree (or null if this tree is a leaf)
	 */
	public Tree<V> firstChild() {
		if (this.childrenList.isEmpty())
			return null;
		return this.childrenList.get(0);
	}

	/**
	 * Returns the last child of this tree (or null if this tree is a leaf)
	 * 
	 * @return the last child of this tree (or null if this tree is a leaf)
	 */
	public Tree<V> lastChild() {
		if (this.childrenList.isEmpty())
			return null;
		int n = this.numberOfChildren();
		return this.childrenList.get(n - 1);
	}

	/**
	 * Returns the number of (immediate) children of this node
	 * 
	 * @return the number of (immediate) children of this node
	 */
	public int numberOfChildren() {
		return this.childrenList.size();
	}

	/**
	 * Returns the index-th child of this tree (counting from zero, as with
	 * arrays). Throws a NoSuchElementException if index is less than zero or
	 * greater than or equal to the number of children
	 * 
	 * @param index
	 *            of the child in the ArrayList
	 * @return index-th child of this tree
	 * @throws NoSuchElementException
	 *             when the child is not in the tree
	 */
	public Tree<V> child(int index) throws NoSuchElementException {
		if (index < 0 || index >= this.childrenList.size()) {
			throw new NoSuchElementException();
		}
		return this.childrenList.get(index);
	}

	/**
	 * Returns an iterator for the children of this tree. The iterator should
	 * have the usual hasNext(), next(), and remove()methods, all of which
	 * should be implemented. (Hint: Java's ArrayList already provides an
	 * iterator.)
	 * 
	 * @return the iterator for the children of this tree
	 */
	public Iterator<Tree<V>> children() {
		return this.childrenList.iterator();
	}

	/**
	 * Returns false if this node has children. This is a convenience routine:
	 * The user could just test node.numberOfChildren() == 0instead.
	 * 
	 * @return false if this node has children and true if has no children
	 */
	public boolean isLeaf() {
		return childrenList.isEmpty();
	}

	/**
	 * Returns true if this tree contains the given node (not an equal node; use
	 * ==, not equals) The root of this tree is included in the recursive
	 * search.
	 * 
	 * @param given
	 *            node (subtree) to check
	 * @return true if this tree contains the given node
	 */
	private boolean contains(Tree<V> node) {
		if (this == node)
			return true;
		while (this.children().hasNext()) {
			Tree<V> temp = this.children().next();
			return temp.contains(node);
		}
		return false;
	}

	/**
	 * Returns true if (1) the given object is a Tree, and (2) the value fields
	 * of the two trees are equal, and (3) each child of one Tree equals the
	 * corresponding child of the other Tree.
	 * 
	 * @param given
	 *            object(tree) to compare
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Tree))
			return false;
		if (this.value == null)
			// cast here as a result of type erasion
			return ((Tree<?>) object).value == null;
		if (!this.value.equals(((Tree<?>) object).value))
			return false;
		return this.value.equals(((Tree<?>) object).value)
				&& this.childrenList.equals(((Tree<?>) object).childrenList);
	}

	/**
	 * @return multi-line print out with indent
	 */
	@Override
	public String toString() {
		return preorder(this, "");
	}

	/**
	 * a private helper function that traverses the tree in preorder, and has an
	 * indentation String as one of its parameters.
	 * 
	 * @param tree
	 *            needs to traverse
	 * @param the
	 *            indent before the value to distinguish different depth
	 */
	private String preorder(Tree<V> tree, String indent) {
		// base case
		String result = indent + tree.value.toString() + '\n';
		Iterator<Tree<V>> iterator = tree.children();
		while (iterator.hasNext()) {
			result += preorder(iterator.next(), indent + "  ");// add indent for
																// every
																// recursion
			// spaces
		}
		return result;
	}

	/**
	 * 
	 * Sets the value in this node of the Tree
	 * 
	 * @param value
	 *            to be set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * Adds newChild as the new last child of this tree, provided that the
	 * resultant tree is valid
	 * 
	 * @param new child to add to the tree
	 * @throws IllegalArgumentException
	 *             when the new child contains the node(parent)
	 */
	public void addChild(Tree<V> newChild) throws IllegalArgumentException {
		// when the tree is child of the new Child
		if (newChild.contains(this)) {
			// String msg = this + " is already in " + newChild;
			throw new IllegalArgumentException();
		}

		childrenList.add(newChild);

	}

	/**
	 * Adds newChild as the new index-th child of this tree (counting from
	 * zero), provided that the resultant tree is valid
	 * 
	 * @param index
	 *            to insert the new child
	 * @param new child to add
	 * @throws IllegalArgumentException
	 *             when index is out of range or new child contains the
	 *             node(parent)
	 */
	public void addChild(int index, Tree<V> newChild)
			throws IllegalArgumentException {
		// if the index is less than zero or
		// greater than (not greater than or equal to) the current number of
		// children,
		// or if the operation would result in an invalid tree
		if (index < 0 || index >= this.numberOfChildren()
				|| newChild.contains(this)) {
			throw new IllegalArgumentException();
		}

		// put the newChild at index
		this.childrenList.add(index, newChild);
	}

	/**
	 * Adds each Tree in children as a new child of this tree node, after any
	 * existing children, provided that the resultant tree is valid
	 * 
	 * @param array
	 *            of children to add
	 * @throws IllegalArgumentException
	 *             when one of the children contains the node
	 */
	@SafeVarargs
	public final void addChildren(Tree<V>... children)
			throws IllegalArgumentException {
		for (Tree<V> child : children) {
			addChild(child);
		}
	}

	/**
	 * Removes and returns the index-th child of this tree, or throws a
	 * NoSuchElementException if the index is illegal
	 * 
	 * @param index
	 *            of the child to be removed
	 * @return the removed child
	 * @throws NoSuchElementException
	 *             when the index is illegal
	 */
	public Tree<V> removeChild(int index) throws NoSuchElementException {
		if (index < 0 || index >= this.numberOfChildren()) {
			throw new NoSuchElementException();
		}
		Tree<V> removedChild = this.child(index);
		this.childrenList.remove(index);
		return removedChild;

	}
}