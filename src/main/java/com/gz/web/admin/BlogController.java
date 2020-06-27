package com.gz.web.admin;

import com.gz.po.Blog;
import com.gz.po.Type;
import com.gz.po.User;
import com.gz.service.BlogService;
import com.gz.service.TagService;
import com.gz.service.TypeService;
import com.gz.util.FileUtils;
import com.gz.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @authod wu
 * @date 2020/5/14 23:19
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }


    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }


    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }



    @PostMapping("/blogs")
    public String post(@RequestParam("id") Long id
            , @RequestParam("title") String title
            , @RequestParam("content") String content
            , @RequestParam("firstPicture") MultipartFile file
            , @RequestParam("flag") String flag
            , @RequestParam(value = "appreciation",required = false,defaultValue = "0") boolean appreciation
            , @RequestParam(value = "shareStatement",required = false,defaultValue = "0") boolean shareStatement
            , @RequestParam(value = "commentabled",required = false,defaultValue = "0") boolean commentabled
            , @RequestParam("published") boolean published
            , @RequestParam(value = "recommend",required = false,defaultValue = "0") boolean recommend
            , @RequestParam("type.id") Long typeId
            , @RequestParam("tagIds") String tagIds
            , @RequestParam("description") String description
            , RedirectAttributes attributes, HttpSession session) {

        FileUtils.upload(file, "E:/image", file.getOriginalFilename());

        Type type = new Type();
        type.setId(typeId);

        Blog blog = new Blog();
        blog.setId(id);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setFlag(flag);
        blog.setAppreciation(appreciation);
        blog.setShareStatement(shareStatement);
        blog.setCommentabled(commentabled);
        blog.setPublished(published);
        blog.setRecommend(recommend);
        blog.setType(type);
        blog.setTagIds(tagIds);
        blog.setDescription(description);
        blog.setFirstPicture(file.getOriginalFilename());


        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }
}
