package io.fares.examples.xml.exception;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StackTraceElementNukeAdapter extends
        XmlAdapter<StackTraceElement, java.lang.StackTraceElement> {

    @Override
    public StackTraceElement marshal(java.lang.StackTraceElement v) throws Exception {
        return null;
    }

    @Override
    public java.lang.StackTraceElement unmarshal(StackTraceElement v) throws Exception {
        return null;
    }

}
