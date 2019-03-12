package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles("hsqldb", "jpa");
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            Arrays.stream(appCtx.getBeanDefinitionNames()).forEach(System.out::println);
        }
    }
}
