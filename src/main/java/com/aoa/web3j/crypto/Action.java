package com.aoa.web3j.crypto;

import java.util.Arrays;

public enum Action {

    ORDINARY_TRX(0, "ordinary or asset transaction"),
    REGISTER_TRX(1, "agent registration transaction"),
    VOTE_TRX(2, "agent vote transaction"),
    ASSET_PUBLISH_TRX(4, "asset publish transaction"),
    CONTRACT_CREATE_TRX(5, "contract create transaction"),
    CONTRACT_CALL_TRX(6, "contract call transaction");


    private final Integer value;
    private final String desc;

    Action(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public static Action getAction(Integer value) {
        return Arrays.stream(Action.values()).filter(aoaAction -> aoaAction.getValue().equals(value)).findFirst()
                     .orElse(ORDINARY_TRX);
    }
}
