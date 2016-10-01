package io.fares.examples.xml.exception;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ThrowableAdapter extends XmlAdapter<SimpleException, java.lang.Throwable> {

    @Override
    public SimpleException marshal(java.lang.Throwable v) throws Exception {
        return new SimpleException();
    }

    @Override
    public java.lang.Throwable unmarshal(SimpleException v) throws Exception {
        return new RuntimeException();
    }

}
