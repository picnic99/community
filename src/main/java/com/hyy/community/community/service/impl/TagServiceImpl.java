package com.hyy.community.community.service.impl;

import com.hyy.community.community.mapper.TagMapper;
import com.hyy.community.community.model.Tag;
import com.hyy.community.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public void addTags(List<Tag> tags) {
        for (Tag tag : tags) {
            Tag temp = tagMapper.getByName(tag.getName());
            if(temp==null){
                tagMapper.insert(tag);
            }
        }

    }

}
