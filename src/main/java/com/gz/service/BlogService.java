package com.gz.service;

import com.gz.po.Blog;
import com.gz.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @authod wu
 * @date 2020/5/28 14:32
 */
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Page<Blog> listBlog(String query,Pageable pageable);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
