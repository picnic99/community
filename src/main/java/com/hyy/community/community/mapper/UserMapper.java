package com.hyy.community.community.mapper;

import com.hyy.community.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select * from user where account=#{account} and password=#{password}")
    User getByAccountAndPassword(String account,String password);
    @Select("select * from user where token=#{token}")
    User findByToken(String token);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(String accountId);
    @Select("select * from user where id=#{id}")
    User findById(String id);
    @Update("update user set name=#{name},account_id=#{accountId},token=#{token},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},bio=#{bio},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);
}