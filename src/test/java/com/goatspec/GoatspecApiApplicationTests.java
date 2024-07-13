package com.goatspec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoatspecApiApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

    @Test
    void mainMethodRunsSuccessfully() {
        GoatspecApiApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }
}
