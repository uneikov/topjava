package ru.javawebinar.topjava.service.jpa;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUpJpa() throws Exception {
        service.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
        testName = getClass().getSuperclass().getSimpleName();
    }
}