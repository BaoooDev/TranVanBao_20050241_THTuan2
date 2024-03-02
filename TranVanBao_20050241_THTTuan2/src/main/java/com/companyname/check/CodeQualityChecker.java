package com.companyname.check;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CodeQualityChecker {

    private static List<String> report = new ArrayList<>();

    public static void main(String[] args) {
        String projectPath = "D:\\Yain\\Hoc phan\\2024_HK2\\kien truc\\tuan2\\src\\main\\java\\com\\companyname\\models\\Triangle.java";  // Đặt đường dẫn thực tế của dự án vào đây
        checkProject(projectPath);
        printReport();
    }

    private static void checkProject(String projectPath) {
        try {
            Files.walk(Paths.get(projectPath))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".java"))
                    .forEach(CodeQualityChecker::checkFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFile(Path filePath) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(filePath);
            cu.accept(new CodeQualityVisitor(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printReport() {
        if (report.isEmpty()) {
            System.out.println("Dự án tuân thủ quy tắc.");
        } else {
            System.out.println("Các vấn đề không tuân thủ quy tắc:");
            for (String issue : report) {
                System.out.println(issue);
            }
        }
    }

    private static class CodeQualityVisitor extends VoidVisitorAdapter<Object> {
        @Override
        public void visit(ClassOrInterfaceDeclaration cls, Object arg) {
            checkClassName(cls);
            checkClassComment(cls);
            checkFields(cls);
            checkMethods(cls);
            super.visit(cls, arg);
        }

        private void checkClassName(ClassOrInterfaceDeclaration cls) {
            if (!Character.isUpperCase(cls.getNameAsString().charAt(0))) {
                report.add("Lớp '" + cls.getNameAsString() + "' không tuân theo quy tắc: Tên lớp phải bắt đầu bằng chữ hoa.");
            }
        }

        private void checkClassComment(ClassOrInterfaceDeclaration cls) {
            if (!cls.getComment().isPresent() || !(cls.getComment().get() instanceof JavadocComment)) {
                report.add("Lớp '" + cls.getNameAsString() + "' không có comment hoặc không phải là JavadocComment.");
            }
        }

        private void checkFields(ClassOrInterfaceDeclaration cls) {
            for (FieldDeclaration field : cls.getFields()) {
                VariableDeclarator variable = field.getVariable(0);
                if (!Character.isLowerCase(variable.getNameAsString().charAt(0))) {
                    report.add("Trường '" + variable.getNameAsString() + "' trong lớp '" + cls.getNameAsString() +
                            "' không tuân theo quy tắc: Tên trường phải bắt đầu bằng chữ thường.");
                }
            }
        }

        private void checkMethods(ClassOrInterfaceDeclaration cls) {
            for (MethodDeclaration method : cls.getMethods()) {
                if (!Character.isLowerCase(method.getNameAsString().charAt(0))) {
                    report.add("Phương thức '" + method.getNameAsString() + "' trong lớp '" + cls.getNameAsString() +
                            "' không tuân theo quy tắc: Tên phương thức phải bắt đầu bằng chữ thường.");
                }
                if (!method.getComment().isPresent() && !isExcludedMethod(method)) {
                    report.add("Phương thức '" + method.getNameAsString() + "' trong lớp '" + cls.getNameAsString() +
                            "' không có comment mô tả công việc của method.");
                }
            }
        }

        private boolean isExcludedMethod(MethodDeclaration method) {
            String methodName = method.getNameAsString();
            return ("hashCode".equals(methodName) || "equals".equals(methodName) || "toString".equals(methodName));
        }


    }
}
