package study.tdd.simpleboard.api.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import study.tdd.simpleboard.api.common.ResponseDTO;
import study.tdd.simpleboard.api.post.domain.*;
import study.tdd.simpleboard.api.post.domain.enums.PostMessage;
import study.tdd.simpleboard.api.post.service.PostService;

import java.time.LocalDateTime;

/**
 * 게시물과 관련된 작업 요청을 처리하는 컨트롤러
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    private final Validator validator;

    /**
     * 게시물 저장 요청을 받아 저장 처리후 반환값으로 저장된 게시물의 postId를 반환합니다.
     *
     * @param dto 게시물 제목, 게시물 내용, 이미지 주소 (선택사항)
     * @return 성공적으로 저장된 게시물의 고유 아이디
     */
    @PostMapping("/posts")
    public ResponseDTO<Long> savePost(@RequestBody PostSaveRequestDTO dto) {
        return new ResponseDTO<>(postService.savePost(dto), PostMessage.SAVE_POST_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseDTO<PostOneDTO> findOnePost(@PathVariable Long postId) {
        return new ResponseDTO<>(postService.findOnePost(postId), PostMessage.FIND_POST_ONE_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseDTO<PageResponseDTO> findPostPage(@RequestParam int page) {
        return new ResponseDTO<>(postService.findPostsPage(page), PostMessage.FIND_POST_PAGE_SUCCESS, HttpStatus.OK);
    }

    @PatchMapping("/posts")
    public ResponseDTO<UpdatedPostDTO> updateOnePost(@RequestBody PostPatchRequestDTO dto) {
        return new ResponseDTO<>(postService.updateOnePost(dto), PostMessage.UPDATE_POST_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseDTO<LocalDateTime> deleteOnePost(@PathVariable Long postId) {
        return new ResponseDTO<>(postService.deleteOnePost(postId), PostMessage.DELETE_POST_SUCCESS, HttpStatus.OK);
    }
}
