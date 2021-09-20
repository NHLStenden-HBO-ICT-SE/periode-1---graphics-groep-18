package com.company;

public class Main {

    public static void main(String[] args) {
    Vector3 x = new Vector3(1, 0, 5);
    Vector3 y = new Vector3(0,2,4);
    double res = x.angleTo(y);

    System.out.println(res);
    }
}
