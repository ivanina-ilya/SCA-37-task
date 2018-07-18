package org.ivanina.course.spring37.cinema.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.EventService;
import org.ivanina.course.sca.cinema.service.UserService;
import org.ivanina.course.sca.cinema.utils.Serializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, JdbcConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class XmlSerializerTest {

    private String listOfUsersXml = "test-upload-users.xml";
    private String listOfEventsXml = "test-upload-events.xml";

    @Autowired
    public Serializer serializer;

    @Autowired
    public UserService userService;

    @Autowired
    public EventService eventService;

    @Test
    public void serializeUserListToXmlTest() throws XMLStreamException, JsonProcessingException {
        List<User> users = userService.getAll().stream().limit(5).collect(Collectors.toList());
        String xml = serializer.serializeUserListToXml(users);
        assertThat(xml, containsString("j.smith@test.com"));
    }

    @Test
    public void deSerializeUsersTest() throws IOException {
        List<User> users = serializer.deSerializeUsers(new FileInputStream( new File(
                ClassLoader.getSystemClassLoader().getResource(listOfUsersXml).getFile())));
        assertEquals(2,users.size());
    }

    @Test
    public void uploadUsersToDbTest() throws IOException {
        List<User> users = serializer.deSerializeUsers(new FileInputStream( new File(
                ClassLoader.getSystemClassLoader().getResource(listOfUsersXml).getFile())));

        Long cntStart = userService.getCount();
        userService.updateOrInsertUsers(users);
        Long cntEnd = userService.getCount();

        assertEquals( cntStart+users.size(), cntEnd+0);
    }


    @Test
    public void serializeEventListToXmlTest() throws XMLStreamException, JsonProcessingException {
        List<Event> events = eventService.getAll().stream().limit(5).collect(Collectors.toList());
        String xml = serializer.serializeEventListToXml(events);
        assertThat(xml, containsString("Harry Potter and The Philosopher's Stone"));
    }

    @Test
    public void deSerializeEventTest() throws IOException {
        List<Event> events = serializer.deSerializeEvent(new FileInputStream( new File(
                ClassLoader.getSystemClassLoader().getResource(listOfEventsXml).getFile())));
        assertEquals(1,events.size());
        assertThat(events.get(0).getName(), containsString("TEST UPLOAD EVENT"));
    }

}
