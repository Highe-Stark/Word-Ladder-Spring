package com.stark.wordladder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import java.io.IOException;

@RestController
public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class);

    @RequestMapping(value="/wordladder", method=RequestMethod.GET)
    public String main (@RequestParam(value="beg", defaultValue = "welcome") String beg,
                        @RequestParam(value="end", defaultValue = "welcome") String end)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        WordLadderServiceIntf wlService = (WordLadderServiceIntf) context.getBean("service");

        String ret;
        try {
            ret = wlService.service(beg, end);
            logger.info("Service served: " + beg + " -> " + end);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            logger.error(ex.getMessage());
            ret = "Oops, the result vanished together with MH370...";
        }
        return ret;
    }
}
