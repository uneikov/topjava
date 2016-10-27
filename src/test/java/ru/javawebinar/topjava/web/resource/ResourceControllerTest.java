package ru.javawebinar.topjava.web.resource;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest{
    private static final String RESOURCES_URL = "/resources/css/style.css";

    @Test
    public void testResourceCSS() throws Exception{
        mockMvc.perform(get(RESOURCES_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("text/css"));
    }

}
