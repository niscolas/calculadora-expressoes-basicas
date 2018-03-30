package AFD;

import Excecoes.ExcecaoExpressaoInvalida;
import Excecoes.ExcecaoPilhaVazia;
import PilhaDinamica.Pilha;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutomatoFinitoDeterministico {

    private double resultadoFinal;
    private OrganizadorDePilha organizador;
    private ResolvedorDeExpressao resolvedor;
    private Scanner entrada;
    private String expressaoEntrada;

    public AutomatoFinitoDeterministico() {
        organizador = new OrganizadorDePilha();
        resolvedor = new ResolvedorDeExpressao();
        entrada = new Scanner(System.in);
        this.MaquinaDeEstados();
    }

    private void MaquinaDeEstados() {
        do {
            try {
                resultadoFinal = EstadoA();
            } catch (ExcecaoPilhaVazia ex) {
                System.err.println(ex.getMessage());
                System.exit(0);
            } catch (NumberFormatException ex2) {
                System.err.println("Expressão Inválida");
                System.exit(0);
            }
            System.out.println("Resultado: " + resultadoFinal);
        } while (!"-1".equals(expressaoEntrada));
        System.exit(0);
    }

    private double EstadoA() throws ExcecaoPilhaVazia {
        Pilha pOrganizada = new Pilha();

        expressaoEntrada = entrada.nextLine();

        organizador.setExpressaoCompleta(expressaoEntrada);
        try {
            pOrganizada = organizador.organizarExpressao();
        } catch (ExcecaoExpressaoInvalida ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
        }

        pOrganizada.invertePilha();

        return EstadoB(pOrganizada);
    }

    private double EstadoB(Pilha pBase) throws ExcecaoPilhaVazia {
        Pilha pAux1 = new Pilha();
        Pilha pAux2 = new Pilha();
        Object elementoSaida;
        int contaFecha = 0;
        int contaAbre = 0;

        do {
            elementoSaida = pBase.desempilha();

            if (elementoSaida.equals("(")) {
                EstadoC(pBase, pAux2, contaFecha, contaAbre);

            } else {
                EstadoD(pAux1, elementoSaida);
            }
            if (pBase.estaVazia() == true
                    && pAux2.estaVazia() == true
                    && pAux1.tamanhoPilha() > 1) {
                EstadoE(pAux1);
            }
        } while (pBase.estaVazia() == false);

        return Double.parseDouble(pAux1.desempilha().toString());
    }

    private void EstadoC(Pilha pBase, Pilha pAux2, int contaFecha, int contaAbre) throws ExcecaoPilhaVazia {
        Object elementoSaida2;

        contaAbre++;

        do {
            elementoSaida2 = pBase.desempilha();

            if (elementoSaida2.equals("(")) {
                pAux2.empilha(elementoSaida2);
                contaAbre++;
            } else if (elementoSaida2.equals(")")) {
                contaFecha++;
                if (contaAbre != contaFecha) {
                    pAux2.empilha(elementoSaida2);
                }
            } else {
                pAux2.empilha(elementoSaida2);
            }
        } while (contaAbre != contaFecha);

        pAux2.invertePilha();
        pBase.empilha(EstadoB(pAux2));
        pAux2.esvaziaPilha();

        contaAbre = 0;
        contaFecha = 0;
    }

    private void EstadoD(Pilha pAux1, Object elementoSaida) throws ExcecaoPilhaVazia {
        pAux1.empilha(elementoSaida);
    }

    private void EstadoE(Pilha pAux1) throws ExcecaoPilhaVazia {
        pAux1.invertePilha();
        Object resultadoExpressao = resolvedor.resolveExpressao(pAux1);
        pAux1.esvaziaPilha();
        pAux1.empilha(resultadoExpressao);
    }
}