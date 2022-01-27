package com.datadoghq.pej;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@RestController
public class BasicController {

    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/Upstream")
    public String service() throws InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String rs = restTemplate.postForEntity("http://localhost:8080/Downstream", new HttpEntity(headers), String.class).getBody();
        Thread.sleep(2000L);
        logger.info("Returned value after post rs: " + rs);
        logger.info("In Upstream");
        return "Ok\n";
    }

    private String doSomeStuff(String somestring) throws InterruptedException {

        String astring;
        astring = String.format("Hello, %s!", somestring);
        Thread.sleep(250L);
        logger.info("In doSomeStuff()");
        return astring;

    }

    @RequestMapping("/Downstream")
    public String downstream() throws InterruptedException {

        Enumeration<String> e = request.getHeaderNames();
        Map<String, String> map = new HashMap<>();

        while (e.hasMoreElements()) {
            // add the names of the request headers into the spanMap
            String key = e.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        doSomeStuff("Hello");
        Thread.sleep(2000L);
        logger.info("In Downstream");
        return "Ok\n";
    }

}