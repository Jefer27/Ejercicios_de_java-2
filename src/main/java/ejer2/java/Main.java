package ejer2.java;

import java.util.Stack;

public class Main {
    public static int evaluar(String expresion) {
        char[] chars = expresion.toCharArray();

        Stack<Integer> numeros = new Stack<Integer>();
        Stack<Character> operadores = new Stack<Character>();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ')
                continue;

            if (chars[i] >= '0' && chars[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                while (i < chars.length && chars[i] >= '0' && chars[i] <= '9')
                    sbuf.append(chars[i++]);
                numeros.push(Integer.parseInt(sbuf.toString()));
            } else if (chars[i] == '(')
                operadores.push(chars[i]);
            else if (chars[i] == ')') {
                while (operadores.peek() != '(')
                    numeros.push(operar(operadores.pop(), numeros.pop(), numeros.pop()));
                operadores.pop();
            } else if (chars[i] == '+' || chars[i] == '-' ||
                    chars[i] == '*' || chars[i] == '/') {
                while (!operadores.empty() && tienePrecedencia(chars[i], operadores.peek()))
                    numeros.push(operar(operadores.pop(), numeros.pop(), numeros.pop()));
                operadores.push(chars[i]);
            }
        }

        while (!operadores.empty())
            numeros.push(operar(operadores.pop(), numeros.pop(), numeros.pop()));

        return numeros.pop();
    }

    public static boolean tienePrecedencia(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static int operar(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("No se puede dividir por cero");
                return a / b;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("5*(2+5) = " + Main.evaluar("5*(2+5)"));
    }
}
