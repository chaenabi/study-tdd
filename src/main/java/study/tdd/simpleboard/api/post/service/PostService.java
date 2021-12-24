package study.tdd.simpleboard.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findOnePost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));
    }
}
