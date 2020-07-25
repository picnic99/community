package com.hyy.community.community.mapper;

import com.hyy.community.community.model.TagQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TagQuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TagQuestion record);

    int insertSelective(TagQuestion record);

    TagQuestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TagQuestion record);

    int updateByPrimaryKey(TagQuestion record);
}