package com.hyy.community.community.mapper;

import com.hyy.community.community.model.Praise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PraiseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Praise record);

    int insertSelective(Praise record);

    Praise selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Praise record);

    int updateByPrimaryKey(Praise record);
    @Select("select * from praise where user_id=#{userId} and relative_id=#{relativeId} and type=#{type}")
    Praise getByParam(Praise praise);
}