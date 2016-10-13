package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.TimeUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPrintTotalResultsTest {
    // get number of test subclasses with Rebound lib (https://bitbucket.org/gigadot/rebound/wiki/Home)
    //private static Rebound r = new Rebound(AbstractPrintTotalResultsTest.class.getPackage().getName());
    //private static int TESTS_COUNT = r.getSubClassesOf(AbstractPrintTotalResultsTest.class).size();
   /* private static String classpath = *//*AbstractPrintTotalResultsTest.class.getPackage().getName();*//*
    AbstractPrintTotalResultsTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();*/
    private static int TESTS_COUNT = getTestsClassCount();
    private final static StringBuilder testResults = new StringBuilder();
    private static final Map<String, Long> testDurations = new HashMap<>();
    private static boolean needToSetHeader = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPrintTotalResultsTest.class);

    private static int getTestsClassCount(){
        String classpath = "C:\\JavaProject\\topjava\\src\\test\\java\\ru\\javawebinar\\topjava\\service\\";
        Path start = Paths.get(classpath);
        try  {
            Stream<Path> stream = Files.find(start, 3, (path, attr) ->
                    String.valueOf(path).startsWith(classpath + "Test"));
            return  (int)stream.count();
        }catch (Exception ex) {return 0;}
    }

    /*private static int getTestsClassCount() {
        String classpath = "ru.javawebinar.topjava.service";
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractPrintTotalResultsTest.class));
        return (int)provider.findCandidateComponents(classpath).stream()
                .filter(item -> item.getBeanClassName().startsWith(classpath + ".Test")).count();
    }*/

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String testName = description.getMethodName();
            long duration = TimeUnit.NANOSECONDS.toMillis(nanos);
            testDurations.put(testName, duration);
        }
    };

    @BeforeClass
    public static void init() {
        needToSetHeader = true;
    }

    public AbstractPrintTotalResultsTest() {
        // Set the header of the results table
        if (needToSetHeader) {
            testResults
                    .append(String.format("%s", TimeUtil.toString(LocalDateTime.now())))
                    .append(String.format("%n%s%n", getClass().getSimpleName()))
                    .append(String.format("===============================%n"))
                    .append(String.format("Test               Duration, ms%n"))
                    .append(String.format("-------------------------------%n"));
        }
        needToSetHeader = false;
    }

    @AfterClass
    public static void print() {
        TESTS_COUNT--;

        testResults
                .append(testDurations.entrySet().stream()
                        .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                        .map(e -> String.format("%-25s %5d%n", e.getKey(), e.getValue()))
                        .collect(Collectors.joining("")))
                .append(String.format("-------------------------------%n"));

        testDurations.clear();

        if (TESTS_COUNT == 0) {
            LOGGER.info(testResults.toString());
        }
    }
}