package io.fares.examples.xml.exception;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "SimpleException")
@XmlAccessorType(XmlAccessType.NONE)
public class SimpleException extends RuntimeException {

    private String id;

    public SimpleException() {
    }

    public SimpleException(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlJavaTypeAdapter(StackTraceElementAdapter.class)
    @XmlElement(name = "StackTraceElement")
    @Override
    public java.lang.StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public void setStackTrace(java.lang.StackTraceElement[] stackTrace) {
        if (stackTrace != null) {
            super.setStackTrace(stackTrace);
        }
    }

}
