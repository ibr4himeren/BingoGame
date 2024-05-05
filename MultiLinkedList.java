/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tombala;

/**
 *
 * @author ieren
 */
public class IbrahimGokdemirMLinkedList<G> {
    
    private IbrahimGokdemirNode<G> head;
    private int length;

    void insertFirst(G data) {
        IbrahimGokdemirNode<G> newNode = new IbrahimGokdemirNode<G>(data);

        if (head == null) {
            head = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        length++;
    }

    void insert(G data) {
        IbrahimGokdemirNode<G> newNode = new IbrahimGokdemirNode<>(data);
        IbrahimGokdemirNode<G> current = head;

        if (head == null) {
            head = newNode;
        } else {
            while (current.down != null) {
                current = current.down;
            }
            current.down = newNode;
        }
        length++;
    }

    void insertAfter(G data, G target) {
        IbrahimGokdemirNode<G> current = head;
        IbrahimGokdemirNode<G> newNode = new IbrahimGokdemirNode<>(data);

        while (current != null && !current.data.equals(target)) {
            current = current.down;
        }

        if (current != null) {
            newNode.down = current.down;
            current.down = newNode;
            length++;
        } else {
            System.out.println("The target data was not found.");
        }
    }

    boolean delete(G data) {
        if (head == null) {
            System.out.println("The list is empty");
            return false;
        } else {
            if (head.data.equals(data)) {
                head = head.down;
                length--;
                return true;
            } else {
                IbrahimGokdemirNode<G> current = head;
                while (current.down != null && !current.down.data.equals(data)) {
                    current = current.down;
                }
                if (current.down != null) {
                    current.down = current.down.down;
                    length--;
                    return true;
                }
            }
        }
        return false;
    }

    public IbrahimGokdemirNode<G> getHead() {
        return head;
    }

    void printList() {
        IbrahimGokdemirNode<G> current = head;
        while (current != null) {
            if (current.down == null) {
                System.out.println(current.data);
                current = current.down;
            } else {
                System.out.print(current.data + ",");
                current = current.down;
            }
        }
    }

    int length() {
        IbrahimGokdemirNode<G> currentRow = head;
        int count = 0;

        while (currentRow != null) {
            IbrahimGokdemirNode<G> currentColumn = currentRow;
            while (currentColumn != null) {
                count++;
                currentColumn = currentColumn.next;
            }
            currentRow = currentRow.down;
        }

        if (count == 0) {
            System.out.println("No Data");
        }

        return count;
    }

    public void matrix_to_multiList(int[][] matrix) {
    IbrahimGokdemirNode<G> prevNode = null;
    IbrahimGokdemirNode<G> currNode = null;

    for (int i = 0; i < matrix.length; i++) {
        IbrahimGokdemirNode<G> previous = null;
        for (int j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] != 0) {
                IbrahimGokdemirNode<G> newNode = new IbrahimGokdemirNode<>((G) Integer.valueOf(matrix[i][j]));

                if (previous != null) {
                    previous.next = newNode;
                } else if (currNode == null) {
                    currNode = newNode;
                }

                if (prevNode != null) {
                    prevNode.down = currNode;
                }

                previous = newNode;
                if (head == null) {
                    head = currNode;
                }
            }
        }
        prevNode = currNode;
        currNode = null;
    }
}

    
}
