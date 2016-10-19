package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Before
    public void setUpJpa() throws Exception {
        service.evictCache();
        testName = getClass().getSuperclass().getSimpleName();
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        Assert.assertEquals(Arrays.asList(ADMIN, USER), all);
        //MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER), all);
    }

    @Override
    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        Assert.assertEquals(Collections.singletonList(ADMIN), service.getAll());
        //MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN), service.getAll());
    }

    @Override
    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser = service.get(created.getId());
        Assert.assertEquals(Arrays.asList(ADMIN, newUser, USER), service.getAll());
        //MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER), service.getAll());
    }
}