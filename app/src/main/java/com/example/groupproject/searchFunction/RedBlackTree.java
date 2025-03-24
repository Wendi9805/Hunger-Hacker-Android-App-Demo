package com.example.groupproject.searchFunction;

import com.example.groupproject.loadDataFunction.FoodData;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Ke Wen
 * @studentId u7588635
 */
public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        String key;
        Set<FoodData> value;
        Node left, right, parent;
        boolean color;

        Node(String key, FoodData data) {
            this.key = key.toLowerCase();
            this.value = new HashSet<>();
            this.value.add(data);
            this.left = null;
            this.right = null;
            this.parent = null;
            this.color = RED;
        }
    }

    private Node root;

    public RedBlackTree() {
        root = null;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) rightChild.left.parent = node;
        rightChild.parent = node.parent;
        if (node.parent == null) root = rightChild;
        else if (node == node.parent.left) node.parent.left = rightChild;
        else node.parent.right = rightChild;
        rightChild.left = node;
        node.parent = rightChild;
        return rightChild;
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) leftChild.right.parent = node;
        leftChild.parent = node.parent;
        if (node.parent == null) root = leftChild;
        else if (node == node.parent.right) node.parent.right = leftChild;
        else node.parent.left = leftChild;
        leftChild.right = node;
        node.parent = leftChild;
        return leftChild;
    }

    private void fixInsert(Node node) {
        Node parent, grandParent;
        while (node != root && node.parent != null && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;
            if (grandParent == null) {
                break;
            }
            if (parent == grandParent.left) {
                Node uncle = grandParent.right;
                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        rotateLeft(node);
                    }
                    parent.color = BLACK;
                    grandParent.color = RED;
                    rotateRight(grandParent);
                }
            } else {
                Node uncle = grandParent.left;
                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rotateRight(node);
                    }
                    parent.color = BLACK;
                    grandParent.color = RED;
                    rotateLeft(grandParent);
                }
            }
        }
        root.color = BLACK;
    }

    public void insert(FoodData foodData) {
        Node newNode = new Node(foodData.getProductName(), foodData);
        if (root == null) {
            root = newNode;
            root.color = BLACK;
        } else {
            Node current = root;
            Node parent = null;
            while (current != null) {
                parent = current;
                int cmp = foodData.getProductName().compareToIgnoreCase(current.key);
                if (cmp < 0) current = current.left;
                else if (cmp > 0) current = current.right;
                else {
                    current.value.add(foodData);
                    return;
                }
            }
            newNode.parent = parent;
            if (foodData.getProductName().compareToIgnoreCase(parent.key) < 0) parent.left = newNode;
            else parent.right = newNode;
            fixInsert(newNode);
        }
    }
    public void clear() {
        root = null;
    }

    public Map<String, Set<FoodData>> buildIndex() {
        Map<String, Set<FoodData>> index = new HashMap<>();
        buildIndexHelper(root, index);
        return index;
    }

    private void buildIndexHelper(Node node, Map<String, Set<FoodData>> index) {
        if (node != null) {
            index.put(node.key, new HashSet<>(node.value));
            buildIndexHelper(node.left, index);
            buildIndexHelper(node.right, index);
        }
    }

}
