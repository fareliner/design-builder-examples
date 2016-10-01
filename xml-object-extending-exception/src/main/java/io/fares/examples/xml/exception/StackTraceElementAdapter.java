package io.fares.examples.xml.exception;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StackTraceElementAdapter extends XmlAdapter<StackTraceElement, java.lang.StackTraceElement> {

    @Override
    public StackTraceElement marshal(java.lang.StackTraceElement v) throws Exception {

        if (v != null) {
            StackTraceElement se = new StackTraceElement();
            se.setMethodName(v.getMethodName());
            se.setDeclaringClass(v.getClassName());
            se.setFileName(v.getFileName());
            se.setLineNumber(v.getLineNumber());
            return se;
        } else {
            return null;
        }
    }

    @Override
    public java.lang.StackTraceElement unmarshal(StackTraceElement v) throws Exception {

        if (v != null) {
            java.lang.StackTraceElement se = new java.lang.StackTraceElement(
                    v.getDeclaringClass(), v.getMethodName(), v.getFileName(),
                    v.getLineNumber());
            return se;
        } else {
            return null;
        }
    }

}
