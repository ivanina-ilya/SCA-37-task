package org.ivanina.course.sca.cinema.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlSerializer implements Serializer {

    private XmlMapper getXmlMapper(){
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper;
    }

    @Override
    public List<User> deSerializeUsers(InputStream inputStream) throws IOException {
        XmlUserListWrapper wrapper = getXmlMapper().readValue(inputStream, XmlUserListWrapper.class);
        return wrapper.getUsers();
    }

    @Override
    public String serializeUserListToXml(List<User> users) throws JsonProcessingException {
        return getXmlMapper().writeValueAsString(new XmlUserListWrapper(users));
    }


    @Override
    public List<Event> deSerializeEvent(InputStream inputStream) throws IOException {
        XmlEventListWrapper wrapper = getXmlMapper().readValue(inputStream, XmlEventListWrapper.class);
        return wrapper.getEvents();
    }

    @Override
    public String serializeEventListToXml(List<Event> events) throws JsonProcessingException {
        return getXmlMapper().writeValueAsString(new XmlEventListWrapper(events));
    }


}
