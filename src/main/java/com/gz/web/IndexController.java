package com.gz.web;

import com.gz.exception.NotFoundException;
import com.gz.po.Blog;
import com.gz.service.BlogService;
import com.gz.service.TagService;
import com.gz.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @authod wu
 * @date 2020/5/11 21:27
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model) {
       model.addAttribute("page",blogService.listBlog(pageable));
       model.addAttribute("types",typeService.listTypeTop(6));
       model.addAttribute("tags",tagService.listTagTop(10));
       model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
       return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            , @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model) {
        List<Blog> blogs = blogService.listRecommendBlogTop(3);
        model.addAttribute("newblogs", blogs);
       // System.out.println(blogs);
        return "_fragments.html :: newblogList";
    }

    @GetMapping("/fetch/{id}")
    public String getAvatar(@PathVariable Long id, HttpSession session) {
        Blog blog = blogService.getBlog(id);
        session.setAttribute("img",blog.getUser().getAvatar());
        return "redirect:/img/" + blog.getUser().getAvatar();
    }

    @GetMapping("/get/{id}")
    public String getImage(@PathVariable Long id) {
        Blog blog = blogService.getBlog(id);
        return "redirect:/img/" + blog.getFirstPicture();
    }
}
