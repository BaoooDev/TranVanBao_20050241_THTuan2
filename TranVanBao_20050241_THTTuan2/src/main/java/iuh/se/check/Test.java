package com.companyname.check;

import java.io.File;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Test {
	void printAllMethods(File javaFile) throws Exception{
		CompilationUnit cu = StaticJavaParser.parse(javaFile);
		List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
		for(MethodDeclaration method: methods) {
			System.out.println(method.getType() + " " + method.getName());
		}
	}
	void printAllFields(File javaFile) throws Exception{
		CompilationUnit cu = StaticJavaParser.parse(javaFile);
		List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
		for(FieldDeclaration field: fields) {
			System.out.println(field);
		}
	}
	public static void main(String[] args) throws Exception{
		File file = new File("D:\\Yain\\Hoc phan\\2024_HK2\\kien truc\\tuan2\\src\\main\\java\\com\\companyname\\models\\Triangle.java");
		Test test = new Test();
		test.printAllFields(file);
		test.printAllMethods(file);
	}
}
