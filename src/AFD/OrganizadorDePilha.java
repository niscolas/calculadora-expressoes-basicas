package AFD;

import Excecoes.ExcecaoExpressaoInvalida;
import Excecoes.ExcecaoPilhaVazia;
import PilhaDinamica.Pilha;

public class OrganizadorDePilha {

    private String expressaoCompleta;

    public OrganizadorDePilha() {
    }

    public OrganizadorDePilha(String expressaoCompleta) throws ExcecaoPilhaVazia {
        this.expressaoCompleta = expressaoCompleta;
    }

    public Pilha organizarExpressao() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        int i = 0;
        double numeroCompleto;
        Object[] elementosExpressao = expressaoCompleta.split("");
        Pilha pilhaOrganizada = new Pilha();
        String concatenaNumero = "";
        String elemento;
        String elementoAnterior = null;

        do {
            elemento = (String) elementosExpressao[i];
            if (elementoAnterior == null) {
                if (!elemento.equals("0") && !elemento.equals("1")
                        && !elemento.equals("2") && !elemento.equals("3")
                        && !elemento.equals("4") && !elemento.equals("5")
                        && !elemento.equals("6") && !elemento.equals("7")
                        && !elemento.equals("8") && !elemento.equals("9")
                        && !elemento.equals("(")) {
                    throw new ExcecaoExpressaoInvalida();
                }
            } else {
                if (elemento.equals("(")) {
                    if (!elementoAnterior.equals("+") && !elementoAnterior.equals("-")
                            && !elementoAnterior.equals("*") && !elementoAnterior.equals("/")
                            && !elementoAnterior.equals("^") && !elementoAnterior.equals("(")) {
                        throw new ExcecaoExpressaoInvalida();
                    }
                } else if (elementoAnterior.equals("(")) {
                    if (!elemento.equals("0") && !elemento.equals("1")
                            && !elemento.equals("2") && !elemento.equals("3")
                            && !elemento.equals("4") && !elemento.equals("5")
                            && !elemento.equals("6") && !elemento.equals("7")
                            && !elemento.equals("8") && !elemento.equals("9")
                            && !elemento.equals("(")) {
                        throw new ExcecaoExpressaoInvalida();
                    }
                } else if (elemento.equals(")")) {
                    if (elementoAnterior.equals("+") || elementoAnterior.equals("-")
                            || elementoAnterior.equals("*") || elementoAnterior.equals("/")
                            || elementoAnterior.equals("^")) {
                        throw new ExcecaoExpressaoInvalida();
                    }
                } else if (elementoAnterior.equals(")")) {
                    if (!elemento.equals("+") && !elemento.equals("-")
                            && !elemento.equals("*") && !elemento.equals("/")
                            && !elemento.equals("^") && !elemento.equals(")")) {
                        throw new ExcecaoExpressaoInvalida();
                    }
                } else if (elemento.equals("+") || elemento.equals("-")
                        || elemento.equals("*") || elemento.equals("/")
                        || elemento.equals("^")) {
                    if (elementoAnterior.equals("+") || elementoAnterior.equals("-")
                            || elementoAnterior.equals("*") || elementoAnterior.equals("/")
                            || elementoAnterior.equals("^")) {
                        throw new ExcecaoExpressaoInvalida();
                    }
                }
            }

            if ("+".equals(elemento) || "-".equals(elemento)
                    || "*".equals(elemento) || "/".equals(elemento)
                    || "^".equals(elemento) || "(".equals(elemento)
                    || ")".equals(elemento)) {
                if (!concatenaNumero.equals("")) {
                    numeroCompleto = Double.parseDouble(concatenaNumero);
                    pilhaOrganizada.empilha(numeroCompleto);
                    concatenaNumero = "";
                }
                pilhaOrganizada.empilha(elementosExpressao[i]);
            } else {
                concatenaNumero += elementosExpressao[i];
            }
            i++;

            elementoAnterior = elemento;
        } while (i != elementosExpressao.length);
        pilhaOrganizada.empilha(concatenaNumero);

        return pilhaOrganizada;
    }

    public String getExpressaoCompleta() {
        return expressaoCompleta;
    }

    public void setExpressaoCompleta(String expressaoCompleta) {
        this.expressaoCompleta = expressaoCompleta;
    }
}
