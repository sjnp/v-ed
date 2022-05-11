package com.ved.backend.service;

import java.util.List;

import com.ved.backend.response.PostResponse;

public interface PostService {

    public PostResponse create(Long courseId, String topic, String detail, String username);

    public PostResponse getPostById(Long questionBoardId);

    public List<PostResponse> getPostByCourseId(Long courseId);

}