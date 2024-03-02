package com.companyname.models;

public class Triangle {
	private float a;
	private float b;
	private float c;
	public float getA() {
		return a;
	}
	public void setA(float a) {
		this.a = a;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	public float getC() {
		return c;
	}
	public void setC(float c) {
		this.c = c;
	}
	public Triangle(float a, float b, float c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public double calcPerimeter(float a, float b, float c) {
		double p = (a + b + c) / 2;
		return p;
	}
	public double calcArea(float a, float b, float c) {
		double p = (a + b + c) / 2;
		double s = Math.sqrt(p*(p-a)*(p-b)*(p-c));
		return s;
	}
	@Override
	public String toString() {
		return "Triangle [a=" + a + ", b=" + b + ", c=" + c + "]";
	}
	
}
