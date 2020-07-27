package com.hyy.community.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.NoticeDTO;
import com.hyy.community.community.enums.CommentTypeEnum;
import com.hyy.community.community.enums.NoticeTypeEnum;
import com.hyy.community.community.mapper.CommentMapper;
import com.hyy.community.community.mapper.NoticeMapper;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.mapper.UserMapper;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.Notice;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<NoticeDTO> getNotice(Long userId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Notice> notices = noticeMapper.getByReceiveId(userId.intValue());
        PageInfo<Notice> noticePageInfo = new PageInfo<Notice>(notices,10);
        List<NoticeDTO> noticeDTOList =new ArrayList<NoticeDTO>();
        for (Notice notice : noticePageInfo.getList()) {
            NoticeDTO noticeDTO = new NoticeDTO();
            User user = userMapper.findById(notice.getSendId().toString());
            noticeDTO.setUser(user);
            if(notice.getType().equals(NoticeTypeEnum.LIKE_QUESTION.getCode())){
                noticeDTO.setTypeMessage(NoticeTypeEnum.LIKE_QUESTION.getMsg());
            }
            if(notice.getType().equals(NoticeTypeEnum.LIKE_COMMENT.getCode())){
                noticeDTO.setTypeMessage(NoticeTypeEnum.LIKE_COMMENT.getMsg());
            }
            if(notice.getType().equals(NoticeTypeEnum.REPLY_QUESTION.getCode())){
                noticeDTO.setTypeMessage(NoticeTypeEnum.REPLY_QUESTION.getMsg());
            }
            if(notice.getType().equals(NoticeTypeEnum.REPLY_COMMENT.getCode())){
                noticeDTO.setTypeMessage(NoticeTypeEnum.REPLY_COMMENT.getMsg());
            }
            if(notice.getType().equals(NoticeTypeEnum.LIKE_QUESTION.getCode())||notice.getType().equals(NoticeTypeEnum.REPLY_QUESTION.getCode())){
                Question question = questionMapper.getById(notice.getRelativeId().intValue());
                noticeDTO.setQuestion(question);
                noticeDTO.setType(CommentTypeEnum.QUESTION.getType());
            }
            if(notice.getType().equals(NoticeTypeEnum.LIKE_COMMENT.getCode())||notice.getType().equals(NoticeTypeEnum.REPLY_COMMENT.getCode())){
                Comment comment = commentMapper.selectByPrimaryKey(notice.getRelativeId());
                noticeDTO.setComment(comment);
                noticeDTO.setType(CommentTypeEnum.COMMENT.getType());
            }
            noticeDTOList.add(noticeDTO);
        }
        PageInfo<NoticeDTO> noticeDTOPageInfo = new PageInfo<>(noticeDTOList,10);
        noticeDTOPageInfo.setNavigatepageNums(noticeDTOPageInfo.getNavigatepageNums());
        noticeDTOPageInfo.setPageNum(noticeDTOPageInfo.getPageNum());
        noticeDTOPageInfo.setPages(noticeDTOPageInfo.getPages());
        noticeDTOPageInfo.setTotal(noticeDTOPageInfo.getTotal());

        return noticeDTOPageInfo;
    }

    @Override
    public Integer getUncheckNoticeCount(Long userId) {
        return noticeMapper.getUncheckNoticeCountByReceiveId(userId.intValue());
    }

}
