package top.naccl.service;

import top.naccl.model.dto.Blog;
import top.naccl.model.dto.BlogVisibility;
import top.naccl.model.vo.BlogDetail;
import top.naccl.model.vo.BlogInfo;
import top.naccl.model.vo.NewBlog;
import top.naccl.model.vo.PageResult;
import top.naccl.model.vo.RandomBlog;
import top.naccl.model.vo.SearchBlog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<top.naccl.entity.Blog> getListByTitleAndCategoryId(String title, Integer categoryId);

    List<SearchBlog> getSearchBlogListByQueryAndIsPublished(String query);

    List<top.naccl.entity.Blog> getIdAndTitleList();

    List<NewBlog> getNewBlogListByIsPublished();

    PageResult<BlogInfo> getBlogInfoListByIsPublished(Integer pageNum);

    PageResult<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum);

    PageResult<BlogInfo> getBlogInfoListByTagNameAndIsPublished(String tagName, Integer pageNum);

    Map<String, Object> getArchiveBlogAndCountByIsPublished();

    List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend();

    void deleteBlogById(Long id);

    void deleteBlogTagByBlogId(Long blogId);

    void saveBlog(Blog blog);

    void saveBlogTag(Long blogId, Long tagId);

    void updateBlogRecommendById(Long blogId, Boolean recommend);

    void updateBlogVisibilityById(Long blogId, BlogVisibility blogVisibility);

    void updateBlogTopById(Long blogId, Boolean top);

    /**
     * 根据博客id更新redis中的博客浏览量，每次加1
     *
     * @param blogId 博客主键id
     */
    void updateViewsToRedis(Long blogId);

    /**
     * 持久化博客浏览量到数据库
     *
     * @param blogId 博客id
     * @param views  浏览量
     */
    void updateViews(Long blogId, Integer views);


    /**
     * 根据博客id获取博客
     *
     * @param id 博客主键id
     * @return 博客对象
     */
    top.naccl.entity.Blog getBlogById(Long id);

    String getTitleByBlogId(Long id);

    /**
     * 根据博客id获取公开博客
     *
     * @param id 博客id
     * @return 博客细节对象
     */
    BlogDetail getBlogByIdAndIsPublished(Long id);

    String getBlogPassword(Long blogId);

    void updateBlog(Blog blog);

    int countBlogByIsPublished();

    int countBlogByCategoryId(Long categoryId);

    int countBlogByTagId(Long tagId);

    Boolean getCommentEnabledByBlogId(Long blogId);

    Boolean getPublishedByBlogId(Long blogId);
}
