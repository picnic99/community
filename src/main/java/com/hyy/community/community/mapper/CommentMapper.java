package com.hyy.community.community.mapper;

import com.hyy.community.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    @Select("select * from comment where type=#{type} and parent_id=#{parentId} order by gmt_create desc")
    List<Comment> listByTypeAndIdDescByTime(Integer type, Integer parentId);
    @Select("select * from comment where type=#{type} and parent_id=#{parentId} order by like_count desc")
    List<Comment> listByTypeAndIdDescByLike(Integer type, Integer parentId);
    @Select("select count(*) from comment where type=#{type} and parent_id=#{parentId}")
    int countByTypeAndId(Integer type, Integer parentId);
}