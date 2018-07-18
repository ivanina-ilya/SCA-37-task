package org.ivanina.course.sca.cinema.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.ivanina.course.sca.cinema.domain.User;

import java.util.List;

@JacksonXmlRootElement(localName = "wrapper")
public class XmlUserListWrapper {

    @JacksonXmlProperty(localName = "users")
    private List<User> users;

    public XmlUserListWrapper(List<User> users) {
        this.users = users;
    }

    public XmlUserListWrapper() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
