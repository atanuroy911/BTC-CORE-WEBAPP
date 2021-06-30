package com.jxust.btcexplorer.btcrpc.entity;

public class CreateWallet {
    private String name;
    private String warning;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
