package io.github.gonster.maven.magics.routes.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>created on 2015/11/18</p>
 *
 * @author Gonster
 */

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/{id}")
    public void show() {}

    @RequestMapping("/new")
    public void create() {}

    @RequestMapping("/del")
    public void del() {}

}
