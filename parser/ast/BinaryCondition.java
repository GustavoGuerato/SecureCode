import java.util.Map;

public class BinaryCondition extends Condition {
    Condition left;
    Condition right;
    String op; // AND / OR

    public BinaryCondition(Condition left, Condition right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public boolean evaluate(Map<String, String> context) {
        return switch (op) {
            case "AND" -> left.evaluate(context) && right.evaluate(context);
            case "OR" -> left.evaluate(context) || right.evaluate(context);
            default -> false;
        };
    }
}