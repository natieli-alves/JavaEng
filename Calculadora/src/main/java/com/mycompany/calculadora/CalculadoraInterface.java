package com.mycompany.calculadora;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.awt.*;
import java.awt.event.*; //biblioteca grafica e tratamento de eventos
import java.util.Arrays; //vo usar matriz para listar os botoes 
import javax.swing.*;
import javax.swing.border.LineBorder; //modificar as bordas do botao 
import javax.swing.JLabel;

        
        
public class CalculadoraInterface {
    int boardWidth = 360; //cria uma janela para fazer a calc desse tam 
    int boardHeight = 540; 
    
    //cores dos botoes
    Color customRosaClaro = new Color(255, 182, 193);
    Color customRosaMedio = new Color(255, 105, 180);
    Color customPreto = new Color(28, 28, 28);
    Color customRosaEscuro = new Color(219, 112, 147); 
    
    //tds os botoes tem q aparecer aq, eles vao ser em matriz
     String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "^", "="
    }; //valores dos botoes
    String[] rightSymbols = {"÷", "×", "-", "+", "=", "^"}; //simbolos de conta
    String[] topSymbols = {"AC", "+/-", "%"}; //simbolos superiores add 
    
    JFrame frame = new JFrame ("Calculadora"); //a janela é um JFrame, dei o nome de frame, cria igual criamos objetos, damos um nome e ele q vai aparecer la emcima da calculadora
    JLabel displayLabel = new JLabel(); //rotulo
    JPanel displayPanel = new JPanel(); //cria o painel 
    JPanel botaoPanel = new JPanel(); //painel dos botoes

    // =======================================================================
    CalculadoraLogica logica = new CalculadoraLogica(); //objeto da lógica
    boolean aguardandoSegundoNumero = false; //controle interno pro segundo valor
    // ========================================================================

    
    //Construtor 
    CalculadoraInterface(){
        frame.setVisible(true); //para a janela ficar visivel 
        frame.setSize(boardWidth, boardHeight); //tam vai ser igual oq definimos la emcima
        frame.setLocationRelativeTo(null); //centralizar a janela 
        frame.setResizable(false); //usuario nao poder mexer na largura das janelas 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //qnd o usuario apertar no X o programa encerra 
        frame.setLayout(new BorderLayout()); //posicionar nort, sul, let, o, na janela  
        
        //rotulo 
        displayLabel.setBackground(customPreto); //fundo preto
        displayLabel.setForeground(Color.WHITE); //cor do texto 
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80)); //fonte e tam 
        displayLabel.setHorizontalAlignment(JLabel.RIGHT); //txt a direita
        displayLabel.setText("0"); //la no topo
        displayLabel.setOpaque(true); //ficar opaco 
        
        //painel
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel); //colocando o rotulo q criamos aq 
        frame.add(displayPanel); //colocamos o painel na nossa janela 
        frame.add(displayPanel, BorderLayout.NORTH); //isso deixa o painel de exibição no norte da janela, pra ficar com os resul la emcima 
        
        //botoes
        botaoPanel.setLayout(new GridLayout(5, 4)); //vai criar aql grade de separacao dos n 
        botaoPanel.setBackground(customPreto); //cor de fundo 
        frame.add(botaoPanel); //add o botao no painel
        
        //add botao 
        for (int i = 0; i < buttonValues.length; i++){
           JButton button = new JButton(); //criar o botao
           String buttonValue = buttonValues[i];
           button.setFont(new Font("Arial", Font.PLAIN, 30)); //definir fonte do bot
           button.setText(buttonValue); 
           button.setFocusable(false); //pra tirar os retang envolta dos bt
           button.setBorder(new LineBorder(customPreto)); //borda do bot
           
           //estilizar os bt 
           if(Arrays.asList(topSymbols).contains(buttonValue)){
               button.setBackground(customRosaClaro); //cor do botao
               button.setForeground(customPreto); //cor da fonte dentro do bot
           }
           else if(Arrays.asList(rightSymbols).contains(buttonValue)){
                if (buttonValue.equals("^")) {
                    button.setBackground(customRosaMedio); // mesma cor dos números
                } else {
                    button.setBackground(customRosaEscuro); // outros operadores
                }
                button.setForeground(Color.WHITE);
            }   
           else{
               button.setBackground(customRosaMedio); //digitos
               button.setForeground(Color.WHITE);
           }
           
           botaoPanel.add(button); //add bt no painel de bt 
           
           //qnd clicar nos bot 
           button.addActionListener(new ActionListener(){ //acao e o click do mouse - eventos q importamos 
            public void actionPerformed(ActionEvent e){
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText(); //identificar qual botao foi apertado 
                    String textoAtual = displayLabel.getText(); //pegar oq ta aparecendo no visor
                    
                    //toda a parte de cálculo agora chama a lógica separada 
                    if(Arrays.asList(rightSymbols).contains(buttonValue)){
                        if (buttonValue.equals("=")) {
                            logica.setB(Double.parseDouble(textoAtual)); //definir segundo número
                            double resultado = logica.calcular(); //faz o cálculo
                            displayLabel.setText(logica.removeZeroDecimal(resultado)); //mostrar o resultado
                            aguardandoSegundoNumero = false;
                        }
                        else if ("+-×÷^".contains(buttonValue)) { //se for o operador
                            logica.setA(Double.parseDouble(textoAtual)); //salva o valor do numero
                            logica.setOperador(buttonValue); //salva o operador
                            aguardandoSegundoNumero = true;
                            displayLabel.setText("0"); //prepara pro segundo numero
                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)){
                        if (buttonValue.equals("AC")) {
                            logica.limpar(); //limpa tudo
                            displayLabel.setText("0");
                        }
                        else if(buttonValue.equals("+/-")){
                            double numDisplay = Double.parseDouble(textoAtual); //converter double
                            double invertido = logica.inverterSinal(numDisplay); //usa método da lógica
                            displayLabel.setText(logica.removeZeroDecimal(invertido)); //converte pra negativo
                        }
                        else if (buttonValue.equals("%")){
                            double numDisplay = Double.parseDouble(textoAtual);
                            double porcent = logica.calcularPorcentagem(numDisplay); //usa método da lógica
                            displayLabel.setText(logica.removeZeroDecimal(porcent));
                        }
                    }
                    // ================================================================================

                    else{ //digitos
                        if (buttonValue.equals(".")){//add so uma casa decimal 
                            if(!displayLabel.getText().contains(buttonValue)){
                            displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if("0123456789".contains(buttonValue)){
                            if(displayLabel.getText().equals("0")){ //se apertar em 0 n pode acontecer nd, ent retorna ele msm
                               displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                        
                }
            });
          
           
        }
        frame.setVisible(true);
    }   
}
