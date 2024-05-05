/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tombala;

/**
 *
 * @author ieren
 */
public class IbrahimGokdemirNode<G> {
    G data;
    IbrahimGokdemirNode<G> next;
    IbrahimGokdemirNode<G> prev;

    public IbrahimGokdemirNode<G> getPrev() {
        return prev;
    }

    public void setPrev(IbrahimGokdemirNode<G> prev) {
        this.prev = prev;
    }

    public IbrahimGokdemirNode<G> getHead() {
        return head;
    }

    public void setHead(IbrahimGokdemirNode<G> head) {
        this.head = head;
    }

    public IbrahimGokdemirNode<G> getDown() {
        return down;
    }

    public void setDown(IbrahimGokdemirNode<G> down) {
        this.down = down;
    }
    IbrahimGokdemirNode<G> head;
    IbrahimGokdemirNode<G> down;

    public IbrahimGokdemirNode(G data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
    
     public G getData() {
        return data;
    }
    
    public void setData(G data) {
        this.data = data;
    }
     public IbrahimGokdemirNode<G> getNext() {
        return next;
    }
     
     public void setNext(IbrahimGokdemirNode<G> next) {
        this.next = next;
    }

}
