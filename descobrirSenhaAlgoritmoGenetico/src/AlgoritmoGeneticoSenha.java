import java.util.Random;

public class AlgoritmoGeneticoSenha {

    private static final String SENHA_CORRETA = "WISLEY2013_CABEÇAO";
    private static final int TAMANHO_POPULACAO = 1000;
    private static final double TAXA_MUTACAO = 0.01;
    private static final int MAX_GERACOES = 100000;
    private static final String CARACTERES_VALIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇabcdefghijklmnopqrstuvwxyzç0123456789!@#$%^&*()-_=+[]{}|;:',.<>?/`~";
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        String[] populacao = new String[TAMANHO_POPULACAO];

        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            populacao[i] = gerarStringAleatoria(SENHA_CORRETA.length());
        }

        int geracao = 0;

        while (geracao < MAX_GERACOES) {

            ordenarPopulacao(populacao);


            if (populacao[0].equals(SENHA_CORRETA)) {
                System.out.println("Senha encontrada na geração " + geracao + ": " + populacao[0]);
                return;
            }


            String[] novaPopulacao = new String[TAMANHO_POPULACAO];


            novaPopulacao[0] = populacao[0];
            novaPopulacao[1] = populacao[1];


            for (int i = 2; i < TAMANHO_POPULACAO; i++) {
                String pai1 = selecionar(populacao);
                String pai2 = selecionar(populacao);
                novaPopulacao[i] = cruzar(pai1, pai2);
            }


            for (int i = 1; i < TAMANHO_POPULACAO; i++) {
                novaPopulacao[i] = mutar(novaPopulacao[i]);
            }

            populacao = novaPopulacao;
            geracao++;

        }

        System.out.println("Limite de gerações atingido. Melhor solução: " + populacao[0]);
    }


    private static String gerarStringAleatoria(int tamanho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            sb.append(CARACTERES_VALIDOS.charAt(RANDOM.nextInt(CARACTERES_VALIDOS.length())));
        }
        return sb.toString();
    }


    private static int calcularFitness(String individuo) {
        int score = 0;
        for (int i = 0; i < individuo.length(); i++) {
            if (individuo.charAt(i) == SENHA_CORRETA.charAt(i)) {
                score++;
            }
        }
        return score;
    }


    private static void ordenarPopulacao(String[] populacao) {
        java.util.Arrays.sort(populacao, (a, b) -> Integer.compare(calcularFitness(b), calcularFitness(a)));
    }


    private static String selecionar(String[] populacao) {
        String individuo1 = populacao[RANDOM.nextInt(TAMANHO_POPULACAO)];
        String individuo2 = populacao[RANDOM.nextInt(TAMANHO_POPULACAO)];
        return calcularFitness(individuo1) > calcularFitness(individuo2) ? individuo1 : individuo2;
    }


    private static String cruzar(String pai1, String pai2) {
        StringBuilder filho = new StringBuilder();
        for (int i = 0; i < pai1.length(); i++) {
            filho.append(RANDOM.nextBoolean() ? pai1.charAt(i) : pai2.charAt(i));
        }
        return filho.toString();
    }


    private static String mutar(String individuo) {
        StringBuilder mutado = new StringBuilder(individuo);
        for (int i = 0; i < individuo.length(); i++) {
            if (RANDOM.nextDouble() < TAXA_MUTACAO) {
                mutado.setCharAt(i, CARACTERES_VALIDOS.charAt(RANDOM.nextInt(CARACTERES_VALIDOS.length())));
            }
        }
        return mutado.toString();
    }
}
