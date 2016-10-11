package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class BaseServiceTest extends AbstractPrintTotalResultsTest{
  /*  private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static StringBuffer results = new StringBuffer();
    static String testName;

    @AfterClass
    public static void printResult() {
        String[] activeProfiles = env.getActiveProfiles();
        System.out.printf("%n----------" + testName + "------------%n");
        System.out.printf(StringUtils.center(
                "Active profiles: " + activeProfiles[0] + " & " + activeProfiles[1], 37)
        );
        System.out.printf("%n-------------------------------------");
        System.out.printf("%n Test                 Duration, ms%n");
        System.out.printf("-------------------------------------");
        System.out.println(results);
        System.out.printf("-------------------------------------%n%n");
        results.setLength(0);
    }

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            results.append(String.format(
                    "%n%-22s %11d",
                    description.getMethodName(),
                    TimeUnit.NANOSECONDS.toMillis(nanos)
            ));
        }
    };*/
}
