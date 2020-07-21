package com.hyy.community.community.mapper;


import com.hyy.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(String token);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(String accountId);
    @Select("select * from user where id=#{id}")
    User findById(String id);
    @Update("update user set name=#{name},account_id=#{accountId},token=#{token},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},bio=#{bio},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);

}
