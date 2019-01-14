package com.example.demo;

import com.example.demo.controller.FileController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFileDown {
    @Autowired
    private FileController fc;

    @Test
    public void testDownload() throws Exception {
        MockHttpServletRequest request = new MockMultipartHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        fc.download(request, response);
        System.out.println("tested download");
    }

    @Test
    public void testNioDownload() throws Exception {
        MockHttpServletRequest request = new MockMultipartHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        fc.nioDownload(request, response);
        System.out.println("test nio download ");

    }

    @Test
    public void CompareTime() throws Exception {
        MockHttpServletRequest request = new MockMultipartHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        long a = System.currentTimeMillis();
        fc.download(request, response);
        long b = System.currentTimeMillis();

        long c = System.currentTimeMillis();
        fc.nioDownload(request, response);
        long d = System.currentTimeMillis();

        System.out.println("io download takes :" + (b - a));
        System.out.println("nio download takes :" + (d - c));

    }
}
