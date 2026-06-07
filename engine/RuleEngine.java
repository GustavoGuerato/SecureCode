import java.util.*;

public class RuleEngine {

    public void execute(List<Rule> rules, Map<String, String> context) {
        for (Rule rule : rules) {
            if (rule.condition.evaluate(context)) {
                System.out.println("RULE MATCH: " + rule.id + " -> " + rule.action);
            }
        }
    }
}