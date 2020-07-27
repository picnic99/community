package com.hyy.community.community.service;

import com.hyy.community.community.model.Praise;

public interface PraiseService {
    public void addLike(Praise praise);
    public void removeLike(Praise praise);

}
