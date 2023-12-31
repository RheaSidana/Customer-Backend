package org.sunbasedata.rhea.sidana.customer.commands;

public enum Command {
    CREATE("create"),
    GET_CUSTOMER_LIST("get_customer_list"),
    DELETE("delete"),
    UPDATE("update");

    private final String cmd;

    Command(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }
}
