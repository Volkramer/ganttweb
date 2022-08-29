package volk.gantt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import volk.gantt.model.Link;

public class LinkResourceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    Link link1 = new Link(1, 2);
    Link link2 = new Link(1, 3);
    Link link3 = new Link(2, 3);

    @Test
    void testAddLink() {

    }

    @Test
    void testDeleteLink() {

    }

    @Test
    void testGetAllLinks() {

    }

    @Test
    void testGetLinkById() {

    }

    @Test
    void testUpdateLink() {

    }
}
