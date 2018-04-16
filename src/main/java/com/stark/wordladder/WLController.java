package com.stark.wordladder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
// import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.io.IOException;

@Controller
public class WLController {

    private static final Logger logger = Logger.getLogger(WLController.class);

    @RequestMapping(value="/")
    public String greet(ModelMap mm) {
        mm.addAttribute("greet", "Welcome to the Hotel California!");
        mm.addAttribute("ladder", "");
        return "index";
    }
    // @RequestMapping(value="/", method=RequestMethod.GET)
    // public ModelAndView greet()
    // {
    //     return new ModelAndView("redirect:/index.jsp");
    // }

    // @RequestMapping(value="/index.jsp")
    // public ModelAndView index() {
    //     try {
    //         return new ModelAndView("template/index.jsp");
    //     }
    //     catch (Exception ex) {
    //         logger.error(ex);
    //         return new ModelAndView("error");
    //     }
    // }
    @ResponseBody
    @RequestMapping(value="/wordladder", method=RequestMethod.GET)
    public String serve (@RequestParam(value="beg", defaultValue = "welcome") String beg,
                        @RequestParam(value="end", defaultValue = "welcome") String end, ModelMap mm)
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
        mm.addAttribute("ladder", ret);
        //return "redirect:index";
        return ret;
    }
}
