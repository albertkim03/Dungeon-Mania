package dungeonmania.entities.Logic;

import dungeonmania.util.Position;

public abstract class LogicRuleEntity extends Logic {
    public static final String AND = "and";
    public static final String OR = "or";
    public static final String XOR = "xor";
    public static final String CO_AND = "co_and";
    private String logicalRule;

    public LogicRuleEntity(Position position, String logicalRule) {
        super(position);
        this.logicalRule = logicalRule;
    }

    public String getLogicalRule() {
        return logicalRule;
    }

    public Boolean isXOR() {
        return logicalRule == XOR;
    }

    public void setActivated(boolean change, Integer tick) {
        if (checkLogicRule(change, tick)) {
            super.setActivated(true);
        } else {
            super.setActivated(false);
        }
    }

    public boolean checkLogicRule(boolean change, Integer tick) {
        switch (logicalRule) {
        case AND:
            // Logic for AND rule
            if (this.sumProcessAdjLogics(true) >= 2) {
                return true;
            } else if (this.sumProcessAdjLogics(true) < 2) {
                return false;
            }
            break;
        case OR:
            // Logic for OR rule
            if (this.sumProcessAdjLogics(true) >= 1) {
                return true;
            } else if (this.sumProcessAdjLogics(true) < 0) {
                return false;
            }
            break;
        case XOR:
            // Logic for XOR rule
            if (this.sumProcessAdjLogics(true) == 1) {
                return true;
            } else if (this.sumProcessAdjLogics(true) != 1) {
                return false;
            }
            break;
        case CO_AND:
            // Logic for CO_AND rule
            if (tick != -1 && this.sumProcessTickAdjLogics(true, tick) >= 2) {
                return true;
            } else if (this.sumProcessTickAdjLogics(true, tick) < 2) {
                return false;
            }
            break;
        default:
            // Handle default case if needed
            break;
        }
        return false;
    }

}
