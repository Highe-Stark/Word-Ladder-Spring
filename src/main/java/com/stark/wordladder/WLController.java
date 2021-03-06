package com.stark.wordladder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class WLController {

    private static final Logger logger = Logger.getLogger(WLController.class);

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;
    /*
     * map "/" to "index.html"
     */
    @RequestMapping(value="/")
    public ModelAndView greet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/index");
        mav.addObject("greet", LocalDate.now());
        return mav;
    }

    /*
     * invoke word ladder service
     */
    @ResponseBody
    @RequestMapping(value="/wordladder", method=RequestMethod.GET)
    public ModelAndView serve (
            @RequestParam(value="beg", defaultValue = "welcome")
                    String beg,
            @RequestParam(value="end", defaultValue = "welcome")
                    String end
    )
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
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/wordladder");
        mav.addObject("ladder", ret);
        return mav;
    }

    /*
     * A simple auth
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView auth (
            @RequestParam(value="username", defaultValue="") String userName,
            @RequestParam(value="pwd", defaultValue = "") String passwd
    )
    {
        logger.info("User: " + userName + " attempts to login with password: " + passwd);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/welcome");
        return mav;
    }

    @RequestMapping(value="/welcome")
    public ModelAndView welcome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/welcome");
        return mav;
    }

    @RequestMapping(value="/errorpage", method=RequestMethod.GET)
    public ModelAndView error(@RequestParam(value="err_msg", defaultValue = "Welcome to Hotel California") String err_msg)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/errorpage");
        mav.addObject("err_msg", err_msg);
        return mav;
    }
}
