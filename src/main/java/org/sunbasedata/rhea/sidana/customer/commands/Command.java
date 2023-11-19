package org.sunbasedata.rhea.sidana.customer.commands;

public enum Command {
    CREATE("create"),
    GET_CUSTOMER_LIST("get_customer_list");

    private final String cmd;

    Command(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }
}
