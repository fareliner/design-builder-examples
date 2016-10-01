package io.fares.examples.xml.exception;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class SimpleExceptionTest {

    @Test
    public void testMarshall() throws Exception {

        JAXBContext jc = JAXBContext.newInstance(SimpleException.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");
//        m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

        SimpleException e = new SimpleException("12");

        StringWriter sw = new StringWriter();

        m.marshal(e, sw);

        System.out.println(sw.toString());

        Unmarshaller u = jc.createUnmarshaller();
        u.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");
        u.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");
//        u.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);

        Object o = u.unmarshal(new StringReader(sw.toString()));

        System.out.println(o);

    }

}
