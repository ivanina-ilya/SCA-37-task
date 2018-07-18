package org.ivanina.course.sca.cinema.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.User;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface Serializer {
    List<User> deSerializeUsers(InputStream inputStream) throws IOException;
    String serializeUserListToXml(List<User> users) throws XMLStreamException, JsonProcessingException;

    List<Event> deSerializeEvent(InputStream inputStream) throws IOException;
    String serializeEventListToXml(List<Event> users) throws XMLStreamException, JsonProcessingException;
}
