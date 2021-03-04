package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.aoa.web3j.core.protocol.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AOAGetDelegateList extends Response<AOAGetDelegateList.DelegateResult> {


    public AOAGetDelegateList.DelegateResult getDelegateResult() {
        return getResult();
    }

    public static class DelegateResult {
        private int delegateNumber;
        private List<DelegateInterface> delegateList;


        public DelegateResult() {
        }

        public DelegateResult(int delegateNumber, List<DelegateInterface> delegateList) {
            this.delegateList = delegateList;
            this.delegateNumber = delegateNumber;
        }

        public int getDelegateNumber() {
            return delegateNumber;
        }

        @JsonDeserialize(using = ResultDelegateDeserialiser.class)
        public void setDelegateList(
            List<DelegateInterface> delegateList) {
            this.delegateList = delegateList;
        }

        public List<DelegateInterface> getDelegateList() {
            return delegateList;
        }

        public void setDelegateNumber(int delegateNumber) {
            this.delegateNumber = delegateNumber;
        }


        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(delegateNumber, delegateList);
        }
    }


    public interface DelegateInterface<T> {
        T get();
    }


    public static class ResultDelegateDeserialiser
        extends JsonDeserializer<List<DelegateInterface>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<DelegateInterface> deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

            List<DelegateInterface> list = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<DelegateObject> delegateObjectIterator =
                    objectReader.readValues(jsonParser, DelegateObject.class);
                while (delegateObjectIterator.hasNext()) {
                    list.add(delegateObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<DelegateObject> delegateObjectIterator =
                    objectReader.readValues(jsonParser, DelegateObject.class);
                while (delegateObjectIterator.hasNext()) {
                    list.add(delegateObjectIterator.next());
                }
            }

            return list;
        }
    }


    public static class DelegateObject extends AOAGetDelegate.Delegate
        implements DelegateInterface<AOAGetDelegate.Delegate> {
        public DelegateObject() {
        }

        public DelegateObject(String address, int vote, String nickName, int registerTime) {
            super(address, vote, nickName, registerTime);
        }

        @Override
        public AOAGetDelegate.Delegate get() {
            return this;
        }
    }


    public static class ResponseDeserialiser extends JsonDeserializer<DelegateResult> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public AOAGetDelegateList.DelegateResult deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, AOAGetDelegateList.DelegateResult.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }


}
