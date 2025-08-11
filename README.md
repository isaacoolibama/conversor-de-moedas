# 💱 Conversor de Moedas

Um sistema profissional de conversão de moedas desenvolvido em Java, com interface de linha de comando elegante e funcionalidades avançadas de rastreamento.

## 🚀 Características

- **Conversão em Tempo Real**: Cotações atualizadas via API Exchange Rate
- **Interface Elegante**: Design ASCII art com cores ANSI
- **Histórico de Conversões**: Rastreamento das últimas 20 conversões
- **Suporte Multi-Moedas**: Dólar, Euro, Real Brasileiro, Peso Argentino, Boliviano, Peso Chileno e Colombiano
- **Formatação Localizada**: Valores formatados nos padrões de cada país
- **Sistema de Logs**: Registro interno de todas as operações
- **Personalização**: Interface adaptada ao nome do usuário

## 🛠️ Tecnologias Utilizadas

- **Java 11+** - Linguagem principal
- **HTTP Client** - Requisições HTTP para API
- **Gson** - Processamento de JSON
- **java.time** - Manipulação de data e hora
- **Collections Framework** - Gerenciamento de dados

## 📋 Pré-requisitos

- Java 11 ou superior
- Conexão com internet para acessar a API
- Dependências do projeto (Gson)

## 🔧 Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/isaacoolibama/conversor-de-moedas.git
   cd conversor-de-moedas
   ```

2. **Configure sua API Key:**
   - Edite o arquivo `src/Main.java`
   - Substitua `"sua_api_key_aqui"` pela sua API key real
   - A URL base já está configurada por padrão
   
   ```java
   // Linha 29 do arquivo Main.java
   private static final String API_KEY = "sua_api_key_aqui";
   ```

3. **Baixe e adicione o Gson (necessário para o IntelliJ e execução local):**
   - Baixe o JAR do Gson em [Maven Central – Gson](https://search.maven.org/artifact/com.google.code.gson/gson)
     - Sugestão: versão 2.10.1 ou superior
   - Crie (se ainda não existir) a pasta `lib/` na raiz do projeto
   - Mova o arquivo `gson-<versão>.jar` para a pasta `lib/`

   #### Adicionar no IntelliJ IDEA
   - Abra: File → Project Structure… (`Ctrl+Alt+Shift+S`)
   - Vá em: Modules → aba Dependencies
   - Clique no `+` → JARs or directories…
   - Selecione `lib/gson-<versão>.jar` e confirme (Scope: Compile)

   Opcional (via build tool):
   - Maven (pom.xml):
     ```xml
     <dependency>
       <groupId>com.google.code.gson</groupId>
       <artifactId>gson</artifactId>
       <version>2.10.1</version>
     </dependency>
     ```
   - Gradle (build.gradle):
     ```groovy
     dependencies {
       implementation 'com.google.code.gson:gson:2.10.1'
     }
     ```

4. **Compile o projeto:**
   - Windows (PowerShell/CMD):
     ```bash
     javac -cp "lib/*" src/Main.java
     ```
   - macOS/Linux:
     ```bash
     javac -cp "lib/*" src/Main.java
     ```

5. **Execute o programa:**
   - Windows (PowerShell/CMD):
     ```bash
     java -cp "src;lib/*" Main
     ```
   - macOS/Linux:
     ```bash
     java -cp "src:lib/*" Main
     ```

### 🔑 Configuração da API Key

Para usar o sistema, você precisa de uma API key da [Exchange Rate API](https://exchangerate-api.com/):

1. Acesse [exchangerate-api.com](https://exchangerate-api.com/)
2. Crie uma conta gratuita
3. Obtenha sua API key
4. Edite o arquivo `src/Main.java` e substitua a linha 29:
   ```java
   private static final String API_KEY = "sua_api_key_aqui";
   ```

## 📱 Como Usar

### 1. Inicialização
- Execute o programa
- Digite seu nome quando solicitado
- O sistema personalizará a interface para você

### 2. Menu Principal
O sistema oferece 9 opções:

| Opção | Conversão | Descrição |
|-------|-----------|-----------|
| 1 | USD → BRL | Dólar Americano → Real Brasileiro |
| 2 | ARS → BRL | Peso Argentino → Real Brasileiro |
| 3 | BOB → BRL | Boliviano → Real Brasileiro |
| 4 | CLP → BRL | Peso Chileno → Real Brasileiro |
| 5 | COP → BRL | Peso Colombiano → Real Brasileiro |
| 6 | BRL → USD | Real Brasileiro → Dólar Americano |
| 7 | EUR → BRL | Euro → Real Brasileiro |
| 8 | 📋 | Exibir Histórico de Conversões |
| 9 | 🚪 | Sair do Sistema |

### 3. Realizando Conversões
1. Escolha a opção desejada (1-7)
2. Digite o valor a ser convertido
3. Aguarde a consulta da API
4. Visualize o resultado formatado

### 4. Histórico
- Acesse a opção 8 para ver o histórico
- Visualize as últimas 20 conversões
- Cada entrada mostra: valores, moedas, taxa e data/hora

## 💰 Formatação das Moedas

O sistema formata automaticamente os valores nos padrões de cada país:

- **Real Brasileiro (BRL)**: R$ 12.004,22
- **Dólar Americano (USD)**: $ 12004.22
- **Euro (EUR)**: € 12004.22
- **Peso Argentino (ARS)**: $ 12004.22
- **Boliviano (BOB)**: Bs 12004.22
- **Peso Chileno (CLP)**: $ 12004
- **Peso Colombiano (COP)**: $ 12004

## 🔌 API Externa

O sistema utiliza a **Exchange Rate API** para obter cotações em tempo real:
- **Base URL**: `https://v6.exchangerate-api.com/v6/`
- **Endpoint**: `/pair/{moeda_origem}/{moeda_destino}`
- **Formato de Resposta**: JSON
- **Atualizações**: Em tempo real

## 📊 Estrutura do Projeto

```
conversor-de-moedas/
├── src/
│   └── Main.java          # Classe principal (edite a API key aqui)
├── lib/                   # Coloque aqui o gson-<versão>.jar
├── README.md             # Este arquivo
└── conversor-moedas-challange.iml  # Configuração do projeto
```

## 🏗️ Arquitetura

### Classes Principais
- **Main**: Classe principal com método main
- **Conversao**: Classe interna para armazenar dados das conversões

### Funcionalidades Principais
- **converterMoeda()**: Realiza conversões via API
- **obterTaxaConversao()**: Consulta taxas de câmbio
- **registrarConversao()**: Armazena no histórico
- **exibirHistorico()**: Mostra conversões anteriores
- **formatarMoeda()**: Formata valores por país

## 🔒 Segurança

- **API Key**: Armazenada como constante (recomenda-se usar variáveis de ambiente em produção)
- **Validação de Entrada**: Verificação de valores positivos
- **Tratamento de Erros**: Try-catch para operações críticas

## 🚧 Limitações Atuais

- Máximo de 20 conversões no histórico
- Dependência de conexão com internet
- Suporte limitado a 7 pares de moedas


## 👥 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

