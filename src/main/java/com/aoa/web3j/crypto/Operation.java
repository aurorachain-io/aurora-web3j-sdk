package com.aoa.web3j.crypto;

public enum Operation {


    ADD_VOTE(0,"加票"),
    Sub_VOTE(1,"减票");

    private int value;
    private String desc;

    Operation(int value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }
}
