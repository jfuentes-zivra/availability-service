package com.zivra.platform.AvailabilityService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AvailabilityControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sayHello() {
        AvailabilityController ctlr = new AvailabilityController();

        String msg = ctlr.sayHello("world").getContent();

        assertEquals("message ok" ,  msg , "Hello world!");

    }
}