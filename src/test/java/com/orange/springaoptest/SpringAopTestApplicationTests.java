package com.orange.springaoptest;

import com.orange.springaoptest.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAopTestApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void test1() {
        userService.searchUserInfo(1l);
    }

    @Test
    public void test2() {
        try {
            userService.searchUserInfo(null);
        }catch (NullPointerException e){

        }
    }
}
