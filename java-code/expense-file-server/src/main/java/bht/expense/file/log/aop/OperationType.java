package bht.expense.file.log.aop;

/**
 * @author 姚轶文
 * @create 2019- 12-25 15:25
 * 操作类型
 */
public enum OperationType {

    /**
     * 操作类型
     */
    UNKNOWN("unknown"),
    LOGIN("login"),
    DELETE("delete"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationType(String s) {
        this.value = s;
    }
}
