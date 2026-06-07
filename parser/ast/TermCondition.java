import java.util.Map;

public class TermCondition extends Condition {
    String key;
    String operator;
    String value;

    public TermCondition(String key, String operator, String value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean evaluate(Map<String, String> context) {
        String ctxValue = context.get(key);

        if (ctxValue == null) return false;

        return switch (operator) {
            case "EQUALS" -> ctxValue.equals(value);
            case "CONTAINS" -> ctxValue.contains(value);
            default -> false;
        };
    }
}