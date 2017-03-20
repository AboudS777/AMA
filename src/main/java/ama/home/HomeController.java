package ama.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Stephane on 2017-03-19.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
