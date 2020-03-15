package br.com.envolvedesenvolve.notacaopolonesareversa;

import android.util.Log;

public class Pilha {

    private Object memo[];
    private int MAX;
    private int topo;

    public Pilha() {
        MAX = 30;
        memo = new Object[MAX];
        topo = -1;
    }

    public boolean isEmpty() {
//        if (topo == -1) {
//            return true;
//        } else {
//            return false;
//        }
        return (topo == -1);
    }

    public boolean isFull() {
        return (topo == MAX - 1);
    }

    public void push(Object x) {
        if (!isFull()) {
            topo++;
            memo[topo] = x;
        } else {
            Log.e("teste","Pilha cheia [Stack Overflow]");
        }
    }

    public Object top() {
        if (!isEmpty()) {
            return memo[topo];
        } else {
            return null;
        }
    }

    public Object pop() {
        if (!isEmpty()) {
            return memo[topo--];
        } else {
            return null;
        }
    }

    public String print() {
        if (!isEmpty()) {
            String resp = "";
            for (int i = 0; i <= topo; i++) {
                resp += memo[i] + ", ";
            }
            return ("P=[" + resp + " ]");
        } else {
            return ("Pilha vazia [Stack Empty]");
        }
    }
}

