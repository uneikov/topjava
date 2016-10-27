package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 10.04.2015.
 */
public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(100007)),
                                hasProperty("description", is("Ужин")),
                                hasProperty("calories", is(510))))))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(100006)),
                                hasProperty("description", is("Обед")),
                                hasProperty("calories", is(1000))))))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(100005)),
                                hasProperty("description", is("Завтрак")),
                                hasProperty("calories", is(500))))))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(100004)),
                                hasProperty("description", is("Ужин")),
                                hasProperty("calories", is(500))))))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(100003)),
                                hasProperty("description", is("Обед")),
                                hasProperty("calories", is(1000))))))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(100002)),
                                hasProperty("description", is("Завтрак")),
                                hasProperty("calories", is(500)))))
                );
    }

}