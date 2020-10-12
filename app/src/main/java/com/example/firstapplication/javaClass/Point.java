package com.example.firstapplication.javaClass;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point() {}

    public void Set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float X() {
        return x;
    }

    public float Y() {
        return y;
    }
}

