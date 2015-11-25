package routes.test.d;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>created on 2015/11/18</p>
 *
 * @author Gonster
 */

@Controller
@RequestMapping("/d")
public class DController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void show() {}

    @RequestMapping(value = "/new", method = {RequestMethod.GET, RequestMethod.POST})
    public void create() {}

    @RequestMapping("/del")
    public void del() {}

}