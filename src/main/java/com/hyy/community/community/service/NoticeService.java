package com.hyy.community.community.service;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    public PageInfo<NoticeDTO> getNotice(Long userId, Integer pageNum, Integer pageSize);
    public Integer getUncheckNoticeCount(Long userId);
}
