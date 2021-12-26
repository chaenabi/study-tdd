package study.tdd.simpleboard.exception.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import study.tdd.simpleboard.exception.common.ErrorCode;

import java.util.Arrays;

/**
 * 게시물 CRUD 에러코드 명세
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
@Getter
@RequiredArgsConstructor
public enum PostCrudErrorCode implements ErrorCode {
    POST_CRUD_FAIL(HttpStatus.BAD_REQUEST, -400, "게시물 처리 요청이 실패했습니다."),
    POST_TITLE_IS_NULL(HttpStatus.BAD_REQUEST, -401, "게시물 제목이 반드시 전달되어야 합니다."),
    POST_TITLE_IS_EMPTY(HttpStatus.BAD_REQUEST, -402, "게시물 제목이 비어 있으면 안됩니다."),
    POST_TITLE_IS_EXCEEDED(HttpStatus.BAD_REQUEST, -403, "게시물 제목은 30 글자를 초과할 수 없습니다."),
    POST_CONTENT_IS_NULL(HttpStatus.BAD_REQUEST, -404, "게시물 내용이 반드시 전달되어야 합니다."),
    POST_CONTENT_IS_EMPTY(HttpStatus.BAD_REQUEST, -405, "게시물 내용이 비어 있으면 안됩니다."),
    POST_CONTENT_IS_EXCEEDED(HttpStatus.BAD_REQUEST, -406, "게시물 내용은 2000 글자를 초과할 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, -407, "해당 게시물을 찾을 수 없습니다."),
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, -408, "해당 페이지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    public Integer findMatchBizCode(String failMessage) {
        return Arrays.stream(PostCrudErrorCode.values())
                .filter(errorCode -> (errorCode.msg).equals(failMessage))
                .map(PostCrudErrorCode::getBizCode)
                .findAny()
                .orElse(-9999);
    }
}
