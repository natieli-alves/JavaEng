package com.mycompany.calculadora;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tem que ser testado todos os metodoso da logica, sendo eles:
 * Calcular() - cal todas operações 
 * divisao por zero
 * multilplicacao, sub e potencia 
 * interterSinal() - do neg pro pov e vise versa 
 * calcularPorcentagem() - autoexplicativo
 * removeZeroDecimal() - pra so aparecer o zero qnd for digitado
 * limpar() - q é o pra limpar qnd aperta em AC
 */
public class CalculadoraTest {

    private CalculadoraLogica calc;

    //antes de cada teste, cria uma nova instância da calculadora
    @BeforeEach
    public void setUp() {
        calc = new CalculadoraLogica();
    }

    //-------------- TESTES DE OPERAÇÕES BÁSICAS --------------

    @Test
    public void testSoma() {
        calc.setA(10);
        calc.setB(5);
        calc.setOperador("+");
        assertEquals(15, calc.calcular(), "10 + 5 deve ser 15");
    }

    @Test
    public void testSubtracao() {
        calc.setA(10);
        calc.setB(5);
        calc.setOperador("-");
        assertEquals(5, calc.calcular(), "10 - 5 deve ser 5");
    }

    @Test
    public void testMultiplicacao() {
        calc.setA(3);
        calc.setB(4);
        calc.setOperador("×");
        assertEquals(12, calc.calcular(), "3 × 4 deve ser 12");
    }

    @Test
    public void testDivisao() {
        calc.setA(8);
        calc.setB(2);
        calc.setOperador("÷");
        assertEquals(4, calc.calcular(), "8 ÷ 2 deve ser 4");
    }

    @Test
    public void testPotenciacao() {
        calc.setA(2);
        calc.setB(3);
        calc.setOperador("^");
        assertEquals(8, calc.calcular(), "2 ^ 3 deve ser 8");
    }

    //-------------- TESTES DE CASOS ESPECIAIS --------------

    @Test
    public void testDivisaoPorZero() {
        calc.setA(5);
        calc.setB(0);
        calc.setOperador("÷");
        assertThrows(ArithmeticException.class, () -> calc.calcular(), "Divisão por zero deve gerar exceção");
    } /*assertThrows (vai rodar e garantir q ele vai lançar exatamente esse tp de erro)
       ArithmeticException.class (qual erro q é)
        () -> calc.calcular() (isso é uma lambda, q qr dizer q é tp um mini bloxo de cod, como se falasse, tenta executar esse metodo aq)
        se o metodo lançar uma ArithmeticException, o teste passa, caso não, o teste falha 
    */

    @Test
    public void testOperadorInvalido() {
        calc.setA(5);
        calc.setB(2);
        calc.setOperador("&"); //operador inválido
        assertThrows(IllegalArgumentException.class, () -> calc.calcular(), "Operador inválido deve gerar exceção");
    }

    //-------------- TESTES DE MÉTODOS SECUNDÁRIOS --------------

    @Test
    public void testInverterSinalPositivo() {
        //aq estamos testando o met invertSinal()
        //qnd passamos o n 5, esperamos q ele retorne -5
        //o asserEquals compara o valor esperado, -5, com o valor retornado
        //se for igual, o teste passa, se n, falha 
        assertEquals(-5, calc.inverterSinal(5), "Inverter sinal de 5 deve ser -5");
    }

    @Test
    public void testInverterSinalNegativo() {
        //msm coisa do positivo so q ao contrario
        assertEquals(10, calc.inverterSinal(-10), "Inverter sinal de -10 deve ser 10");
    }

    @Test
    public void testPorcentagem() {
        //verifica o metodo calcularPorcentagem()
        //qnd passamos 25, esperase q o resultado seja 0.25
        //se o met retornar corretamente passa, se n, n
        assertEquals(0.25, calc.calcularPorcentagem(25), "25% deve ser 0.25");
    }

    //-------------- TESTES DE FORMATAÇÃO DE NÚMEROS --------------

    //esse teste vai verificar se o met removeZeroDecimal, esta exibindo os numeros da forma correta, sem aquele .0 desnecessario
    @Test
    public void testRemoveZeroDecimalInteiro() {
        String resultado = calc.removeZeroDecimal(10.0);
        assertEquals("10", resultado, "10.0 deve virar '10'");
    }

    @Test
    public void testRemoveZeroDecimalDecimal() {
        String resultado = calc.removeZeroDecimal(10.5);
        assertEquals("10.5", resultado, "10.5 deve continuar '10.5'");
    }

    //-------------- TESTE DE LIMPEZA DE DADOS --------------

    @Test
    public void testLimpar() {
        calc.setA(20);
        calc.setB(10); //define os valores
        calc.setOperador("+"); 
        calc.limpar(); //resetar
        //para verificar o estado interno, chama de novo os setters e garante que está limpo
        calc.setOperador("+");
        calc.setA(2);
        calc.setB(3);
        assertEquals(5, calc.calcular(), "Depois de limpar, deve funcionar normalmente");
    }

    //-------------- TESTES DE COMBINAÇÕES E FLUXOS COMPLETOS --------------

    @Test
    public void testSequenciaDeOperacoesSimples() {
        //simula seq, 10 + 5, depois resultado × 2
        calc.setA(10);
        calc.setB(5);
        calc.setOperador("+");
        double soma = calc.calcular();

        calc.setA(soma); //usa o result anterior como entrada
        calc.setB(2);
        calc.setOperador("×");
        double resultadoFinal = calc.calcular();

        assertEquals(30, resultadoFinal, "((10 + 5) × 2) deve ser 30");
    }

    @Test
    public void testOperacaoComDecimais() {
        calc.setA(2.5);
        calc.setB(0.5);
        calc.setOperador("+");
        assertEquals(3.0, calc.calcular(), 0.0001, "2.5 + 0.5 deve ser 3.0");
    } //o terceiro argumento (0.0001) é a margem de erro permitida para números decimais.

    @Test
    public void testPotenciaComZero() {
        calc.setA(0);
        calc.setB(5);
        calc.setOperador("^");
        assertEquals(0, calc.calcular(), "0 elevado a 5 deve ser 0");
    }

    @Test
    public void testPotenciaDeZero() {
        calc.setA(5);
        calc.setB(0);
        calc.setOperador("^");
        assertEquals(1, calc.calcular(), "5 elevado a 0 deve ser 1");
    }

    @Test
    public void testSubtracaoNegativa() {
        calc.setA(3);
        calc.setB(7);
        calc.setOperador("-");
        assertEquals(-4, calc.calcular(), "3 - 7 deve ser -4");
    }

    @Test
    public void testMultiplicacaoPorZero() {
        calc.setA(99);
        calc.setB(0);
        calc.setOperador("×");
        assertEquals(0, calc.calcular(), "Qualquer número × 0 deve ser 0");
    }

    @Test
    public void testDivisaoDecimal() {
        calc.setA(5);
        calc.setB(2);
        calc.setOperador("÷");
        assertEquals(2.5, calc.calcular(), 0.0001, "5 ÷ 2 deve ser 2.5");
    }
}
