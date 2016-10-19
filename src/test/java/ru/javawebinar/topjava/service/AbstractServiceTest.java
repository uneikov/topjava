package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;

/**
 * User: gkislin
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(ACTIVE_DB)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractServiceTest implements EnvironmentAware{

    private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceTest.class);

    private static StringBuilder results = new StringBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static String testName;

    @AfterClass
    public static void printResult() {
        String[] activeProfiles = env.getActiveProfiles();
        System.out.printf("%n------" + testName + "--------%n");
        System.out.printf(
                "Active profiles: " + activeProfiles[0] + " & " + activeProfiles[1], 37
        );
        System.out.printf("%n-------------------------------------");
        System.out.printf("%n Test                 Duration, ms%n");
        System.out.printf("-------------------------------------");
        System.out.println(results);
        System.out.printf("-------------------------------------%n%n");
        results.setLength(0);
    }

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-
    // to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            results.append(String.format(
                    "%n%-24s %11d",
                    description.getMethodName(),
                    TimeUnit.NANOSECONDS.toMillis(nanos)
            ));
        }
    };
}