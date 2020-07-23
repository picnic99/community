package com.hyy.community.community.mapper;

import com.hyy.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

    @Update("update question " +
            "set title=#{title},description=#{description},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},creator=#{creator},tag=#{tag}" +
            "where id=#{id}")
    void update(Question question);
    @Select("select * from question")
    List<Question> list();
    @Select("select * from question where creator=#{createId}")
    List<Question> listByCreateId(Integer createId);
    @Select("select * from question where id=#{id}")
    Question getById(Integer id);
    @Update("update question set view_count=view_count+1 where id=#{id}")
    void incView(Integer id);
}