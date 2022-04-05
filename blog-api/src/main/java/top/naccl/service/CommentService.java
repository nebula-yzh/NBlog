package top.naccl.service;

import top.naccl.entity.Comment;
import top.naccl.model.vo.PageComment;

import java.util.List;

public interface CommentService {
	List<Comment> getListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);

	/**
	 * 获取所有评论列表
	 * @param page 当前页面
	 * @param blogId 博客id
	 * @param parentCommentId 父评论
	 * @return 当前文章根评论，和所有子评论（在一个list中）
	 */
	List<PageComment> getPageCommentList(Integer page, Long blogId, Long parentCommentId);

	Comment getCommentById(Long id);

	void updateCommentPublishedById(Long commentId, Boolean published);

	void updateCommentNoticeById(Long commentId, Boolean notice);

	void deleteCommentById(Long commentId);

	void deleteCommentsByBlogId(Long blogId);

	void updateComment(Comment comment);

	Long countByPageAndIsPublished(Integer page, Long blogId);

	void saveComment(top.naccl.model.dto.Comment comment);
}
