import java.util.*;

public class Main {

    public static void main(String[] args) {

        String input = "REGRA BLOCK_ACCESS NIVEL BAIXA SE TOKEN CONTAINS \"admin\" AND USER_ROLE EQUALS \"guest\" ACAO REJEITAR FIM";

        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer.tokenize());
        List<Rule> rules = parser.parse();

        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.analyze(rules);

        RuleEngine engine = new RuleEngine();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSecureCode CLI started (type 'exit' to quit)");

        while (true) {
            System.out.print("log> ");
            String line = scanner.nextLine();

            if (line == null || line.isBlank()) {
                System.out.println("Empty input ignored");
                continue;
            }

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down...");
                break;
            }

            Map<String, String> context = parseLogStrict(line);

            if (context.isEmpty()) {
                System.out.println("No valid key=value pairs found. Input ignored.");
                continue;
            }

            engine.execute(rules, context);
        }

        scanner.close();
    }

    private static Map<String, String> parseLogStrict(String line) {

        Map<String, String> context = new HashMap<>();

        String[] parts = line.trim().split("\\s+");

        for (String p : parts) {

            if (!p.contains("=")) {
                System.out.println("[WARN] invalid token ignored: " + p);
                continue;
            }

            String[] kv = p.split("=", 2);

            if (kv.length != 2) {
                System.out.println("[WARN] malformed token ignored: " + p);
                continue;
            }

            String key = kv[0].trim();
            String value = kv[1].trim();

            if (key.isEmpty() || value.isEmpty()) {
                System.out.println("[WARN] empty key/value ignored: " + p);
                continue;
            }

            context.put(key, value);
        }

        return context;
    }
}