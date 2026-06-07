import java.util.*;

public class Parser {

    private List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // ponto de entrada
    public List<Rule> parse() {
        List<Rule> rules = new ArrayList<>();

        while (!match(TokenType.EOF)) {
            rules.add(parseRule());
        }

        return rules;
    }

    // REGRA <id> NIVEL <prioridade> SE <condicao> ACAO <resultado> ;
    private Rule parseRule() {

        consume(TokenType.REGRA, "Expected REGRA");

        String id = consume(TokenType.IDENTIFIER, "Expected rule id").value;

        consume(TokenType.NIVEL, "Expected NIVEL");

        String priority = consumePriority();

        consume(TokenType.SE, "Expected SE");

        Condition condition = parseCondition();

        consume(TokenType.ACAO, "Expected ACAO");

        String action = consumeAction();

        consume(TokenType.FIM, "Expected ; (FIM)");

        return new Rule(id, priority, condition, action);
    }

    // condição lógica (AND / OR)
    private Condition parseCondition() {
        Condition left = parseTerm();

        while (peekIs(TokenType.AND) || peekIs(TokenType.OR)) {
            String op = advance().type.name();
            Condition right = parseTerm();
            left = new BinaryCondition(left, right, op);
        }

        return left;
    }

    // TOKEN CONTAINS "x"
    private Condition parseTerm() {

        String key = consume(TokenType.IDENTIFIER, "Expected key").value;

        Token op = advance(); // CONTAINS / EQUALS / etc

        String value = consume(TokenType.STRING, "Expected string").value;

        return new TermCondition(key, op.type.name(), value);
    }

    // helpers
    private boolean match(TokenType type) {
        if (pos >= tokens.size()) return false;
        return tokens.get(pos).type == type;
    }

    private boolean peekIs(TokenType type) {
        return match(type);
    }

    private Token advance() {
        return tokens.get(pos++);
    }

    private Token consume(TokenType type, String error) {
        if (match(type)) return advance();
        throw new RuntimeException(error + " at position " + pos);
    }

    private String consumePriority() {
        Token t = advance();

        if (t.type == TokenType.BAIXA ||
            t.type == TokenType.MEDIA ||
            t.type == TokenType.ALTA ||
            t.type == TokenType.CRITICA) {
            return t.type.name();
        }

        throw new RuntimeException("Invalid priority");
    }

    private String consumeAction() {
        Token t = advance();

        if (t.type == TokenType.REJEITAR ||
            t.type == TokenType.NOTIFICAR ||
            t.type == TokenType.SOLICITAR_MFA ||
            t.type == TokenType.LOG_ERROR) {
            return t.type.name();
        }

        throw new RuntimeException("Invalid action");
    }
}