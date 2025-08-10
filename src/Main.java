//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    // Constantes para cores ANSI
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    
    // Substitua pela sua API key da Exchange Rate API
    // Obtenha em: https://exchangerate-api.com/
    private static final String API_KEY = "sua_api_key_aqui";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();
    
    // Estruturas para histórico e logs
    private static List<Conversao> historicoConversoes = new ArrayList<>();
    private static String nomeUsuario = "";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        // Verificar se a API key foi configurada
        if (API_KEY.equals("sua_api_key_aqui")) {
            System.err.println(RED + "❌ ERRO: API Key não configurada!" + RESET);
            System.err.println(YELLOW + "📝 Edite o arquivo src/Main.java e substitua 'sua_api_key_aqui' pela sua API key" + RESET);
            System.err.println(YELLOW + "🌐 Obtenha sua chave gratuita em: https://exchangerate-api.com/" + RESET);
            System.err.println(YELLOW + "💡 Exemplo: private static final String API_KEY = \"sua_chave_real_aqui\";" + RESET);
            System.exit(1);
        }
        
        Scanner scanner = new Scanner(System.in);
        int opcao;

        exibirBanner();
        solicitarNomeUsuario(scanner);
        exibirBoasVindas();

        do {
            exibirMenu();
            System.out.print(CYAN + "➤ Escolha uma opção (1-9): " + RESET);
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    converterMoeda("USD", "BRL", "Dólar Americano", "Real Brasileiro", scanner);
                    break;
                case 2:
                    converterMoeda("ARS", "BRL", "Peso Argentino", "Real Brasileiro", scanner);
                    break;
                case 3:
                    converterMoeda("BOB", "BRL", "Boliviano", "Real Brasileiro", scanner);
                    break;
                case 4:
                    converterMoeda("CLP", "BRL", "Peso Chileno", "Real Brasileiro", scanner);
                    break;
                case 5:
                    converterMoeda("COP", "BRL", "Peso Colombiano", "Real Brasileiro", scanner);
                    break;
                case 6:
                    converterMoeda("BRL", "USD", "Real Brasileiro", "Dólar Americano", scanner);
                    break;
                case 7:
                    converterMoeda("EUR", "BRL", "Euro", "Real Brasileiro", scanner);
                    break;
                case 8:
                    exibirHistorico();
                    break;
                case 9:
                    exibirDespedida();
                    break;
                default:
                    System.out.println(RED + "❌ Opção inválida! Tente novamente." + RESET);
            }

            if (opcao != 9) {
                System.out.println(YELLOW + "\n⏸️  Pressione Enter para continuar..." + RESET);
                scanner.nextLine(); // Consumir o \n restante
                scanner.nextLine(); // Aguardar Enter
                limparTela();
            }

        } while (opcao != 9);

        scanner.close();
    }

    private static void solicitarNomeUsuario(Scanner scanner) {
        System.out.print(CYAN + "👋 Por favor, digite seu nome: " + RESET);
        nomeUsuario = scanner.nextLine();
        if (nomeUsuario.trim().isEmpty()) {
            nomeUsuario = "Usuário";
        }
    }

    private static void exibirBanner() {
        System.out.println(PURPLE + BOLD);
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    💱 CONVERSOR DE MOEDAS 💱                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝" + RESET);
    }

    private static void exibirBoasVindas() {
        System.out.println(GREEN + "\n🌟 Olá, " + nomeUsuario + "! Bem-vindo ao Conversor de Moedas!");
        System.out.println("📊 Cotações atualizadas em tempo real" + RESET);
        System.out.println();
    }

    private static void exibirMenu() {
        System.out.println(BLUE + BOLD + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + BOLD + "║                      MENU PRINCIPAL                          ║" + RESET);
        System.out.println(BLUE + BOLD + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println("║ " + CYAN + "1. USD → 🇧🇷 BRL    (Dólar Americano → Real Brasileiro)" + RESET);
        System.out.println("║ " + CYAN + "2. 🇦🇷 ARS → 🇧🇷 BRL    (Peso Argentino → Real Brasileiro)" + RESET );
        System.out.println("║ " + CYAN + "3. 🇧🇴 BOB → 🇧🇷 BRL    (Boliviano → Real Brasileiro)" + RESET);
        System.out.println("║ " + CYAN + "4. 🇨🇱 CLP → 🇧🇷 BRL    (Peso Chileno → Real Brasileiro)" + RESET);
        System.out.println("║ " + CYAN + "5. 🇨🇴 COP → 🇧🇷 BRL    (Peso Colombiano → Real Brasileiro)" + RESET);
        System.out.println("║ " + CYAN + "6. 🇧🇷 BRL → USD    (Real Brasileiro → Dólar Americano)" + RESET);
        System.out.println("║ " + CYAN + "7. 🇪🇺 EUR → 🇧🇷 BRL    (Euro → Real Brasileiro)" + RESET);
        System.out.println("║ " + YELLOW + "8. 📋 Exibir Histórico de Conversões" + RESET);
        System.out.println("║ " + RED + "9. 🚪 Sair do Sistema" + RESET);
        System.out.println(BLUE + BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET);
    }

    private static void limparTela() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        exibirBanner();
        exibirBoasVindas();
    }

    private static void exibirDespedida() {
        System.out.println(GREEN + "\n🎉 Obrigado por utilizar o Conversor de Moedas, " + nomeUsuario + "!");
        System.out.println(PURPLE + BOLD + "\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ✅ SISTEMA ENCERRADO ✅                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝" + RESET);
    }

    private static String criarLinha(int tamanho) {
        StringBuilder linha = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            linha.append("=");
        }
        return linha.toString();
    }

    private static String criarLinhaDecorada(int tamanho) {
        StringBuilder linha = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            linha.append("═");
        }
        return linha.toString();
    }

    // Método para realizar conversões entre moedas
    // Utiliza variáveis para armazenar valores e aplica taxas de conversão
    private static void converterMoeda(String moedaOrigem, String moedaDestino,
                                       String nomeOrigem, String nomeDestino, Scanner scanner) {
        try {
            System.out.println(GREEN + "\n" + criarLinhaDecorada(50));
            System.out.println("🔄 " + nomeOrigem + " → " + nomeDestino);
            System.out.println(criarLinhaDecorada(50) + RESET);
            
            System.out.print(CYAN + "💰 Digite o valor em " + nomeOrigem + ": " + RESET);
            double valor = scanner.nextDouble();

            if (valor <= 0) {
                System.out.println(RED + "❌ Erro: O valor deve ser maior que zero!" + RESET);
                return;
            }

            System.out.println(YELLOW + "\n⏳ Consultando cotações em tempo real..." + RESET);
            double taxaConversao = obterTaxaConversao(moedaOrigem, moedaDestino);
            
            if (taxaConversao > 0) {
                // Aplicando a lógica de conversão com as taxas obtidas
                double resultado = valor * taxaConversao;
                exibirResultado(valor, resultado, nomeOrigem, nomeDestino, taxaConversao);
                
                // Registrar conversão no histórico
                registrarConversao(valor, resultado, nomeOrigem, nomeDestino, taxaConversao);
                
                // Registrar log da conversão (sem exibir para o usuário)
                registrarLogConversao(valor, resultado, nomeOrigem, nomeDestino, taxaConversao);
            } else {
                System.out.println(RED + "❌ Erro ao obter taxa de conversão. Verifique sua conexão com a internet." + RESET);
            }

        } catch (Exception e) {
            System.out.println(RED + "❌ Erro na entrada de dados. Tente novamente." + RESET);
            scanner.nextLine(); // Limpar buffer
        }
    }

    private static double obterTaxaConversao(String moedaOrigem, String moedaDestino) {
        try {
            // Usando HttpClient para fazer requisições
            String urlString = BASE_URL + API_KEY + "/pair/" + moedaOrigem + "/" + moedaDestino;

            // Configurando HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header("User-Agent", "ConversorMoedas/1.0")
                    .GET()
                    .build();

            // Usando HttpResponse para gerenciar respostas
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Análise da resposta JSON usando Gson
                return processarRespostaJSON(response.body(), moedaDestino);
            } else {
                System.out.println("Erro na API: Código " + response.statusCode());
                return -1;
            }

        } catch (Exception e) {
            System.out.println("Erro de conexão: " + e.getMessage());
            return -1;
        }
    }

    private static double processarRespostaJSON(String jsonResponse, String moedaDestino) {
        try {
            // Usando JsonParser e JsonObject do Gson
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (jsonObject.has("result") && jsonObject.get("result").getAsString().equals("success")) {
                // Filtrando moedas usando Gson
                if (jsonObject.has("conversion_rate")) {
                    return jsonObject.get("conversion_rate").getAsDouble();
                }
            } else if (jsonObject.has("error-type")) {
                System.out.println("Erro na API: " + jsonObject.get("error-type").getAsString());
            }

            return -1;

        } catch (Exception e) {
            System.out.println("Erro ao processar resposta da API: " + e.getMessage());
            return -1;
        }
    }

    // Método para exibir resultados das conversões
    private static void exibirResultado(double valorOrigem, double valorDestino,
                                        String nomeOrigem, String nomeDestino, double taxaConversao) {
        System.out.println(GREEN + "\n" + criarLinhaDecorada(50));
        System.out.println("🎯 RESULTADO DA CONVERSÃO:");
        System.out.println(criarLinhaDecorada(50) + RESET);
        
        String valorOrigemFormatado = formatarMoeda(valorOrigem, nomeOrigem);
        String valorDestinoFormatado = formatarMoeda(valorDestino, nomeDestino);
        
        System.out.println(CYAN + "📊 " + valorOrigemFormatado + " = " + valorDestinoFormatado + RESET);
        System.out.println(YELLOW + "💱 Taxa de conversão: 1 " + nomeOrigem + " = " + 
            String.format("%.4f %s", taxaConversao, nomeDestino) + RESET);
        System.out.println(GREEN + criarLinhaDecorada(50) + RESET);
    }
    
    // Método para formatar moedas nos padrões dos países
    private static String formatarMoeda(double valor, String nomeMoeda) {
        if (nomeMoeda.contains("Real Brasileiro")) {
            return String.format("R$ %.2f", valor).replace(".", ",");
        } else if (nomeMoeda.contains("Dólar Americano")) {
            return String.format("$ %.2f", valor);
        } else if (nomeMoeda.contains("Peso Argentino")) {
            return String.format("$ %.2f", valor);
        } else if (nomeMoeda.contains("Boliviano")) {
            return String.format("Bs %.2f", valor);
        } else if (nomeMoeda.contains("Peso Chileno")) {
            return String.format("$ %.0f", valor);
        } else if (nomeMoeda.contains("Peso Colombiano")) {
            return String.format("$ %.0f", valor);
        } else if (nomeMoeda.contains("Euro")) {
            return String.format("€ %.2f", valor);
        } else {
            return String.format("%.2f", valor);
        }
    }
    
    // Método para registrar conversão no histórico
    private static void registrarConversao(double valorOrigem, double valorDestino, 
                                          String nomeOrigem, String nomeDestino, double taxaConversao) {
        Conversao conversao = new Conversao(valorOrigem, valorDestino, nomeOrigem, nomeDestino, taxaConversao);
        historicoConversoes.add(conversao);
        
        // Manter apenas as últimas 20 conversões
        if (historicoConversoes.size() > 20) {
            historicoConversoes.remove(0);
        }
    }
    
    // Método para exibir histórico de conversões
    private static void exibirHistorico() {
        if (historicoConversoes.isEmpty()) {
            System.out.println(YELLOW + "\n📋 Nenhuma conversão realizada ainda." + RESET);
            return;
        }
        
        System.out.println(PURPLE + BOLD + "\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📋 HISTÓRICO DE CONVERSÕES 📋                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝" + RESET);
        
        for (int i = historicoConversoes.size() - 1; i >= 0; i--) {
            Conversao conv = historicoConversoes.get(i);
            System.out.println(CYAN + String.format("%d. %s → %s", 
                historicoConversoes.size() - i, conv.getNomeOrigem(), conv.getNomeDestino()) + RESET);
            System.out.println("   " + formatarMoeda(conv.getValorOrigem(), conv.getNomeOrigem()) + 
                " = " + formatarMoeda(conv.getValorDestino(), conv.getNomeDestino()));
            System.out.println("   Taxa: " + String.format("%.4f", conv.getTaxaConversao()) + 
                " | Data: " + conv.getDataHora());
            System.out.println();
        }
    }
    
    // Método para registrar log da conversão (sem exibir para o usuário)
    private static void registrarLogConversao(double valorOrigem, double valorDestino, 
                                             String nomeOrigem, String nomeDestino, double taxaConversao) {
        LocalDateTime agora = LocalDateTime.now();
        String log = String.format("[%s] %s: %.2f %s → %.2f %s (Taxa: %.4f)", 
            agora.format(formatter), nomeUsuario, valorOrigem, nomeOrigem, valorDestino, nomeDestino, taxaConversao);
        
        // Log registrado internamente, sem exibir para o usuário
        // System.out.println(GREEN + "📝 Log registrado: " + log + RESET);
    }
    
    // Classe interna para representar uma conversão
    private static class Conversao {
        private double valorOrigem;
        private double valorDestino;
        private String nomeOrigem;
        private String nomeDestino;
        private double taxaConversao;
        private LocalDateTime dataHora;
        
        public Conversao(double valorOrigem, double valorDestino, String nomeOrigem, 
                        String nomeDestino, double taxaConversao) {
            this.valorOrigem = valorOrigem;
            this.valorDestino = valorDestino;
            this.nomeOrigem = nomeOrigem;
            this.nomeDestino = nomeDestino;
            this.taxaConversao = taxaConversao;
            this.dataHora = LocalDateTime.now();
        }
        
        // Getters
        public double getValorOrigem() { return valorOrigem; }
        public double getValorDestino() { return valorDestino; }
        public String getNomeOrigem() { return nomeOrigem; }
        public String getNomeDestino() { return nomeDestino; }
        public double getTaxaConversao() { return taxaConversao; }
        public String getDataHora() { return dataHora.format(formatter); }
    }
}
