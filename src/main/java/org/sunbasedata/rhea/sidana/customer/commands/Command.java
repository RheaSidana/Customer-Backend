package org.sunbasedata.rhea.sidana.customer.commands;

public enum Command {
    CREATE("create");

    private final String cmd;

    Command(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }
}
