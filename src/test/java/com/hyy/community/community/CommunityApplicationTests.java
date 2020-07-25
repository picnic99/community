package com.hyy.community.community;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
import com.hyy.community.community.mapper.CommentMapper;
import com.hyy.community.community.mapper.TagMapper;
import com.hyy.community.community.model.Tag;
import com.hyy.community.community.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagMapper tagMapper;


    @Test
    void contextLoads() {
    }

    @Test
    public void test1(){
        Tag tag = new Tag();
        tag.setName("面试题");
        tag.setCreator(1L);
        tag.setGmtCreate(System.currentTimeMillis());
        tagMapper.insert(tag);
        System.out.println(tag.getId());


    }
}
