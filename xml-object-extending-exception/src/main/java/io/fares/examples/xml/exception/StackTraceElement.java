package io.fares.examples.xml.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "StackTraceElement")
@XmlType(name = "StackTraceElement")
public class StackTraceElement implements Serializable {

    private final static long serialVersionUID = 301L;

    protected String declaringClass;

    protected String methodName;

    protected int lineNumber;

    protected String fileName;


    @XmlElement(required = true)
    public String getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(String value) {
        this.declaringClass = value;
    }

    @XmlElement(required = true)
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String value) {
        this.methodName = value;
    }

    @XmlAttribute()
    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int value) {
        this.lineNumber = value;
    }

    @XmlAttribute(required = true)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }


}
