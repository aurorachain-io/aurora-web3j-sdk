package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

import com.aoa.web3j.core.protocol.core.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AOAVoteOperation extends Response<AOAVoteOperation.VoteOperation>{

    public Optional<VoteOperation> getVoteOperation() {
        return Optional.ofNullable(getResult());
    }

    public static class VoteOperation  {


        private String candidate;
        private int operation;

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

        public VoteOperation() {
        }

        public VoteOperation(String candidate, int operation) {
            this.candidate = candidate;
            this.operation = operation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            VoteOperation voteOperation = (VoteOperation) o;
            return candidate.equals(voteOperation.candidate) &&
                   operation == voteOperation.operation ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(candidate, operation);
        }

        @Override
        public String toString() {
            return "{" +
                   "candidate='" + candidate + '\'' +
                   ", operation=" + operation +
                   '}';
        }
    }


    public static class ResponseVoteDeserialiser extends JsonDeserializer<VoteOperation> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public VoteOperation deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, VoteOperation.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
