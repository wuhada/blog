package com.gz.web;

import com.gz.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.stream.Stream;

/**
 * @authod wu
 * @date 2020/6/2 15:48
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/take")
    public String getImage(HttpSession session) {
        String img = (String) session.getAttribute("img");
        return "redirect:/img/" + img;
    }
}
