package com.hyy.community.community.mapper;

import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insert(Question question);
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

}

