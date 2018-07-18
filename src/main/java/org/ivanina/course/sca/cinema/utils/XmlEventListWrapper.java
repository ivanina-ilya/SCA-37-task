package org.ivanina.course.sca.cinema.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.ivanina.course.sca.cinema.domain.Event;

import java.util.List;

@JacksonXmlRootElement(localName = "wrapper")
public class XmlEventListWrapper {
    @JacksonXmlProperty(localName = "events")
    private List<Event> events;

    public XmlEventListWrapper(List<Event> events) {
        this.events = events;
    }

    public XmlEventListWrapper() {
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
