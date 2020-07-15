package com.cfyj.data.structure.tree;

/**
 * 二叉搜索树: 指一棵空树或者具有下列性质的二叉树: 
 * 1.左子树所有节点的值都小于根节点 
 * 2.右子树所有节点的值都大于根节点
 * 3.左右子树又分别为二叉搜索树,满足他的额、特点
 * 
 * @author chenfeng
 *
 */
public class Tree<T extends Comparable<T>> {

	private TreeNode<T> root;

	public Tree(T value) {
		root = new TreeNode<>(value);
	}

	public void add(T value) {
		/**
		 * 找到合适位置,替换元素,
		 */

		TreeNode<T> newNode = new TreeNode<>(value);
		TreeNode<T> tmpNode = root;
		while (tmpNode != null) {
			if (value.compareTo(tmpNode.getValue()) == 0) {
				// 相等,丢弃
				break;
			} else if (value.compareTo(tmpNode.getValue()) > 0) {
				// value 大
				if (tmpNode.getRightNode() != null) {
					tmpNode = tmpNode.getRightNode(); // 右边还有元素,与右边比较
				} else {
					tmpNode.setRightNode(newNode); // 右边无元素,直接插入到当前节点的右边
					break;
				}

			} else {
				// value小
				if (tmpNode.getLeftNode() != null) {
					tmpNode = tmpNode.getLeftNode(); // 右边还有元素,与右边比较
				} else {
					tmpNode.setLeftNode(newNode); // 右边无元素,直接插入到当前节点的右边
					break;
				}
			}

		}

	}
	
	public boolean contains(T value) {
		TreeNode<T> tmpNode = root;
		boolean flag = false ; 
		while (tmpNode != null) {
			if (value.compareTo(tmpNode.getValue()) == 0) {
				// 相等
				flag = true ; 
				break;
			} else if (value.compareTo(tmpNode.getValue()) > 0) {
				// value 大
				if (tmpNode.getRightNode() != null) {
					tmpNode = tmpNode.getRightNode(); // 右边还有元素,与右边比较
				} else {
					break;
				}
			} else {
				// value小
				if (tmpNode.getLeftNode() != null) {
					tmpNode = tmpNode.getLeftNode(); // 右边还有元素,与右边比较
				} else {
					break;
				}
			}

		}	
		return flag;
	}
	
	
	public static void main(String[] args) {
		Tree<Integer> tree = new Tree<Integer>(10);
		tree.add(15);
		tree.add(6);
		tree.add(12);
		tree.add(8);
		System.out.println(tree.contains(15));
		System.out.println(tree.contains(8));
		System.out.println(tree.contains(1));
	}
	
}

class TreeNode<T extends Comparable<T>> {
	private T value;

	private TreeNode<T> leftNode;

	private TreeNode<T> rightNode;

	public TreeNode(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TreeNode<T> getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(TreeNode<T> leftNode) {
		this.leftNode = leftNode;
	}

	public TreeNode<T> getRightNode() {
		return rightNode;
	}

	public void setRightNode(TreeNode<T> rightNode) {
		this.rightNode = rightNode;
	}

	@Override
	public String toString() {
		return "TreeNode [value=" + value + ", leftNode=" + leftNode + ", rightNode=" + rightNode + "]";
	}

	
}