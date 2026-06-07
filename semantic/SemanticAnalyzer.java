import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {

    private final List<String> errors = new ArrayList<>();
    private final List<String> warnings = new ArrayList<>();

    public void analyze(List<Rule> rules) {
        for (Rule rule : rules) {
            validateRule(rule);
        }

        report();
    }

    private void validateRule(Rule rule) {

        if (rule == null) {
            errors.add("Null rule detected");
            return;
        }

        validateId(rule);
        validatePriority(rule);
        validateAction(rule);
        validateCondition(rule);
        validatePriorityVsAction(rule);
    }

    private void validateId(Rule rule) {
        if (rule.id == null || rule.id.isBlank()) {
            errors.add("Rule with missing or empty ID");
        }
    }

    private void validatePriority(Rule rule) {
        if (rule.priority == null || rule.priority.isBlank()) {
            errors.add("Rule " + safeId(rule) + " missing priority");
            return;
        }

        switch (rule.priority) {
            case "BAIXA":
            case "MEDIA":
            case "ALTA":
            case "CRITICA":
                break;
            default:
                errors.add("Rule " + safeId(rule) + " has invalid priority: " + rule.priority);
        }
    }

    private void validateAction(Rule rule) {
        if (rule.action == null || rule.action.isBlank()) {
            errors.add("Rule " + safeId(rule) + " missing action");
            return;
        }

        switch (rule.action) {
            case "REJEITAR":
            case "NOTIFICAR":
            case "SOLICITAR_MFA":
            case "LOG_ERROR":
                break;
            default:
                errors.add("Rule " + safeId(rule) + " has invalid action: " + rule.action);
        }
    }

    private void validateCondition(Rule rule) {
        if (rule.condition == null) {
            errors.add("Rule " + safeId(rule) + " has null condition");
        }
    }

    // regra do edital (importante)
    private void validatePriorityVsAction(Rule rule) {

        if (rule.priority == null || rule.action == null) return;

        if (rule.priority.equals("BAIXA") && rule.action.equals("REJEITAR")) {
            warnings.add(
                "Rule " + safeId(rule) +
                ": BAIXA priority using REJEITAR may be semantically inconsistent"
            );
        }
    }

    private String safeId(Rule rule) {
        return (rule.id == null) ? "UNKNOWN" : rule.id;
    }

    private void report() {
        if (!errors.isEmpty()) {
            System.out.println("SEMANTIC ERRORS:");
            for (String e : errors) {
                System.out.println("- " + e);
            }
        } else {
            System.out.println("Semantic analysis passed.");
        }

        if (!warnings.isEmpty()) {
            System.out.println("\nSEMANTIC WARNINGS:");
            for (String w : warnings) {
                System.out.println("- " + w);
            }
        }
    }
}