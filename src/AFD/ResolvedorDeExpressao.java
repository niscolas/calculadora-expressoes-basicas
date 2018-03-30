package AFD;

import Excecoes.ExcecaoPilhaVazia;
import PilhaDinamica.Pilha;

public class ResolvedorDeExpressao {

    private Pilha pBase;
    private double resultadoDaExpressao;

    public ResolvedorDeExpressao() {
    }

    public ResolvedorDeExpressao(Pilha pBase) {
        this.pBase = pBase;
        resultadoDaExpressao = 0;
    }

    public Object resolveExpressao(Pilha pBase) throws ExcecaoPilhaVazia {
        this.setpBase(pBase);
        this.achaOperacao();
        return this.getResultadoDaExpressao();
    }

    public void achaOperacao() throws ExcecaoPilhaVazia {
        Pilha pAux = new Pilha();
        double operando1, operando2;
        String operador;
        Object elementoSaida;
        Object[] operadores = {"^", "*", "+"};
        Object[] operadores2 = {"^", "/", "-"};

        for (int i = 0; i < 3; i++) {
            do {
                elementoSaida = pBase.desempilha();

                if (elementoSaida.equals(operadores[i]) || elementoSaida.equals(operadores2[i])) {
                    operador = (String) elementoSaida;
                    operando1 = Double.parseDouble(pAux.desempilha().toString());
                    operando2 = Double.parseDouble(pBase.desempilha().toString());
                    pAux.empilha(resolveOperacao(operando1,
                            (String) operador,
                            operando2));
                } else {
                    pAux.empilha(elementoSaida);
                }
            } while (pBase.estaVazia() == false);
            do {
                pBase.empilha(pAux.desempilha());
            } while (pAux.estaVazia() == false);
        }
        resultadoDaExpressao = Double.parseDouble(pBase.desempilha().toString());
    }

    public double resolveOperacao(double op1, String oper, double op2) {
        double resultado = 0;
        switch (oper) {
            case "^":
                resultado = op1;
                if (op1 == 1 || op2 == 0) {
                    return 1;
                }
                for (int i = 1; i < op2; i++) {
                    resultado = resultado * op1;
                }
                break;

            case "*":
                resultado = op1 * op2;
                break;

            case "/":
                resultado = op1 / op2;
                break;

            case "+":
                resultado = op1 + op2;
                break;

            case "-":
                resultado = op1 - op2;
                break;
        }
        return resultado;
    }

    public Pilha getpBase() {
        return pBase;
    }

    public void setpBase(Pilha pBase) {
        this.pBase = pBase;
    }

    public Object getResultadoDaExpressao() {
        return resultadoDaExpressao;
    }
}
