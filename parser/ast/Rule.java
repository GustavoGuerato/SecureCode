public class Rule {
    String id;
    String priority;
    Condition condition;
    String action;

    public Rule(String id, String priority, Condition condition, String action) {
        this.id = id;
        this.priority = priority;
        this.condition = condition;
        this.action = action;
    }
}