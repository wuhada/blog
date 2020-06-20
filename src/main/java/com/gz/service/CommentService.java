package com.gz.service;

import com.gz.po.Comment;

import java.util.List;

/**
 * @authod wu
 * @date 2020/5/30 18:12
 */
public interface CommentService {

    public Comment getComment(Long id);

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
