package com.mycompany.calculadora;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

//classe separada apenas para a parte lógica da calculadora, sem interface gráfica
public class CalculadoraLogica {
    
    //variáveis que guardam os valores e o operador selecionado
    private double A;
    private double B;
    private String operador;

    //método para definir o valor de A (primeiro número digitado)
    public void setA(double a) {
        this.A = a;
    }

    //método para definir o valor de B (segundo número digitado)
    public void setB(double b) {
        this.B = b;
    }

    //método para definir qual operador foi escolhido (+, -, ×, ÷, ^)
    public void setOperador(String operador) {
        this.operador = operador;
    }

    //método principal que faz os cálculos conforme o operador escolhido
    public double calcular() {
        switch (operador) { //verifica qual operação deve ser feita
            case "+": //soma
                return A + B;
            case "-": //subtração
                return A - B;
            case "×": //multiplicação
                return A * B;
            case "÷": //divisão
                if (B == 0) throw new ArithmeticException("Divisão por zero"); //impede erro ao dividir por 0
                return A / B;
            case "^": //potenciação
                return Math.pow(A, B); //usa função pronta de potência do Java
            default:
                //se o operador não for nenhum dos acima, gera um erro
                throw new IllegalArgumentException("Operador inválido: " + operador);
        }
    }

    //inverte o sinal do número (positivo ↔ negativo)
    public double inverterSinal(double valor) {
        return valor * -1;
    }

    //calcula a porcentagem de um número
    public double calcularPorcentagem(double valor) {
        return valor / 100;
    }

    //função que remove o .0 de números inteiros (ex: 5.0 → 5)
    public String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) { //verifica se o número é inteiro
            return Integer.toString((int) numDisplay); //converte pra int e devolve como texto
        }
        return Double.toString(numDisplay); //se for decimal, mantém o formato normal
    }

    //limpa todos os valores (usado quando aperta o botão AC)
    public void limpar() {
        this.A = 0;
        this.B = 0;
        this.operador = null;
    }
}
