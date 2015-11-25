package routes.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>created on 2015/11/18</p>
 *
 * @author Gonster
 */

@Controller
@RequestMapping({"/test", "/test0"})
public class TestController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void show() {}

    @RequestMapping(value = "/new", method = {RequestMethod.GET, RequestMethod.POST})
    public void create() {}

    @RequestMapping("/del")
    public void del() {}

}

@Controller
@RequestMapping(value = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
class Test2Controller {

    @RequestMapping("/{id}")
    public void show() {}

    @RequestMapping("/del")
    public void del() {}

}