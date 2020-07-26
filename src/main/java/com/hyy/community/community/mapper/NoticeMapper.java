package com.hyy.community.community.mapper;

import com.hyy.community.community.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    @Select("select * from notice where receive_id=#{receiveId} and status!=2 order by gmt_create desc")
    List<Notice> getByReceiveId(Integer receiveId);

    @Select("select count(*) from notice where receive_id=#{receiveId} and status!=2")
    int getUncheckNoticeCountByReceiveId(Integer receiveId);
}