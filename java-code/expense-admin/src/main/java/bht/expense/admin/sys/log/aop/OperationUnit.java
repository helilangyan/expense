package bht.expense.admin.sys.log.aop;

/**
 * @author 姚轶文
 * @create 2019- 12-25 15:23
 * 操作单元
 */
public enum OperationUnit {

    UNKNOWN("unknown"),
    USER("user"),
    ROLE("role"),
    AUTHORITY("authority"),
    RESOURCE("resource"),
    SYSTEM("system"),
    CLIENT("client"),
    CONTRACT("contract"),
    EMPLOYEE("employee"),
    FINANCE("finance"),
    ORG("org"),
    PERSONNEL("personnel"),
    TIMESHEET("timesheet"),
    EXPAT("expat"),
    TASK("task"),
    WORKFLOW("workflow");

    private String value;

    OperationUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
