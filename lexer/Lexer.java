import java.util.*;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                pos++;
                continue;
            }

            if (c == '"') {
                tokens.add(readString());
                continue;
            }

            if (Character.isLetter(c)) {
                tokens.add(readWord());
                continue;
            }

            pos++;
        }

        tokens.add(new Token(TokenType.EOF, "", 0, 0));
        return tokens;
    }

    private Token readString() {
        pos++;
        StringBuilder sb = new StringBuilder();

        while (pos < input.length() && input.charAt(pos) != '"') {
            sb.append(input.charAt(pos++));
        }

        pos++;
        return new Token(TokenType.STRING, sb.toString(), 0, 0);
    }

    private Token readWord() {
        StringBuilder sb = new StringBuilder();

        while (pos < input.length() &&
                (Character.isLetterOrDigit(input.charAt(pos)) || input.charAt(pos) == '_')) {
            sb.append(input.charAt(pos++));
        }

        String word = sb.toString().toUpperCase();

        return new Token(keywordOrId(word), word, 0, 0);
    }

    private TokenType keywordOrId(String w) {
        try {
            return TokenType.valueOf(w);
        } catch (Exception e) {
            return TokenType.IDENTIFIER;
        }
    }
}