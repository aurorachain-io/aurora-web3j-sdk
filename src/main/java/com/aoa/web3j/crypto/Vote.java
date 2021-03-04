package com.aoa.web3j.crypto;



import com.aoa.web3j.utils.Numeric;

import java.io.Serializable;

public class Vote implements Serializable{

    private static final long serialVersionUID = 3643015059444703638L;

    public Vote(){}


    String candidate;
    int operation;

    private Vote(String candidate,int operation){
        this.candidate = candidate;
        this.operation = operation;
    }

    public static Vote createVote(String address,Operation operation){
        byte[] addressBytes = Numeric.hexStringToByteArray(address);
        // byte[] addressBytes = address.getBytes();
        return new Vote(address,operation.getValue());
    }


    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }


    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}
