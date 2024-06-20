import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class OraculoCinematografico {

    public static void main(String[] args) {

        BufferedReader br;
        String line;
        Set<String> conjunto = new HashSet<>();
        Map<String, Integer> relacao = new HashMap<>();
        Map<Integer, String> relacaoInv = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(args[0]));
            int intRelacao = 0;
            while ((line = br.readLine()) != null) {

                String[] elemementos = line.split("/");
                boolean first = true;

                for (String e : elemementos) {
                    String tratanome;

                    if (first) {
                        tratanome = e.toLowerCase();
                        first = false;
                    } else {
                        String[] words = e.split(",");
                        if (words.length < 2)
                            tratanome = e.trim().split(" ")[0].toLowerCase();
                        else {
                            String lastName = words[0].toLowerCase();
                            String firstName = words[1].trim().split(" ")[0].toLowerCase();
                            tratanome = firstName + " " + lastName;
                        }
                    }

                    if (!relacao.keySet().contains(tratanome)) {
                        relacao.put(tratanome, intRelacao);
                        relacaoInv.put(intRelacao, tratanome);
                        intRelacao++;
                        if (intRelacao % 1000 == 0)
                            System.out.println("Criando relação de filmes/atores => " + (intRelacao));
                    }
                }
            }

            System.out.println("Criado um dicionário com " + relacao.size() + " pares chave e valor");
            Graph g = new Graph(relacao.size());
            System.out.println("O nro total de componentes/vertices é " + conjunto.size());

            System.out.println("Alimentando o grafo");
            br = new BufferedReader(new FileReader(args[0]));
            while ((line = br.readLine()) != null) {
                String[] elementos = line.split("/");
                String nomeDoFilme = elementos[0].toLowerCase();
                int filmeVertexID = relacao.get(nomeDoFilme);
                // System.out.println("Filme -> " + nomeDoFilme);

                for (int i = 1; i < elementos.length; i++) {
                    String tratanome;
                    String[] words = elementos[i].split(",");
                    if (words.length < 2)
                        tratanome = elementos[i].trim().split(" ")[0].toLowerCase();
                    else {
                        String lastName = words[0].toLowerCase();
                        String firstName = words[1].trim().split(" ")[0].toLowerCase();
                        tratanome = firstName + " " + lastName;
                    }
                    // System.out.println(" * Ator sendo buscado -> " + tratanome + " (" + i + ")");
                    if (relacao.keySet().contains(tratanome)) {
                        int elencoVertexID = relacao.get(tratanome);
                        g.addEdge(filmeVertexID, elencoVertexID);
                        if (g.E() % 1000 == 0)
                            System.out.println("Foram criadas " + g.E() + " relacoes");
                    } else {
                        System.out
                                .println("  Erro -> Filme (" + nomeDoFilme + ") ->Não encontrei a chave " + tratanome);
                    }
                }
            }
            System.out.println("Ao total foram criadas " + g.E() + " relacoes no grafo");

            Scanner sc = new Scanner(System.in);
            Integer ref = null;
            do {
                System.out.print("Informe o nome de um ator qq: ");
                String atorRef = sc.nextLine();
                ref = relacao.get(atorRef);
                if (ref == null)
                    System.out.println("Ator não encontrado");
                else
                    System.out.println("o id do ator é " + ref);

            } while (ref == null);

            CaminhamentoEmLargura oraculo = new CaminhamentoEmLargura(g, ref);

            Integer tgt = null;
            do {
                System.out.print("Informe o nome do segundo ator: ");
                String atorTgt = sc.nextLine();
                tgt = relacao.get(atorTgt);
                if (tgt == null)
                    System.out.println("Ator tgt não encontrado");
                else
                    System.out.println("o id do ator tgt é " + tgt);
            } while (tgt == null);

            if (oraculo.hasPath(tgt)) {
                System.out.println("Eles se conhecem");
                for (Integer i : oraculo.pathTo(tgt)) {
                    System.out.print(relacaoInv.get(i) + ", ");
                }

            } else {
                System.out.println("Eles NÃO se conhecem");
            }

        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }

    }

}
