package study.tdd.simpleboard.exception.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class GeneralControllerAdvice {

    /**
     * 정형화된 에러 응답메시지 포맷을 생성합니다.
     *
     * @param httpStatus 발생한 에러
     * @param e          익셉션 목록
     * @return ResponseEntity<ErrorResponseDTO>
     */
    public static ResponseEntity<ErrorResponseDTO> handleGeneralException(HttpStatus httpStatus, Exception... e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(Arrays.stream(e)
                        .filter(Objects::nonNull).findFirst()
                        .map(Exception::getMessage)
                        .orElse(httpStatus.getReasonPhrase()))
                .build();
        log.error(response.getMessage());
        return new ResponseEntity<>(response, getHttpHeader(), httpStatus);
    }

    /**
     * 정형화된 에러 응답메시지 포맷을 생성합니다.
     * 이 메서드는 @Valid 검증을 통해 BindingResult 정보를 가져오는 익셉션을 위해 사용
     *
     * @param httpStatus 발생한 에러
     * @param e          @Valid 또는 @Validated 검증을 하는 익셉션 목록
     * @return ResponseEntity<ErrorResponseDTO>
     */
    public static ResponseEntity<ErrorResponseDTO> handleValidParameterException(HttpStatus httpStatus, ErrorCode errorCode, InvalidParameterException... e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(Arrays.stream(e)
                        .filter(Objects::nonNull).findFirst()
                        .map(Exception::getMessage)
                        .orElse(httpStatus.toString()))
                .errors(Arrays.stream(e)
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElseThrow(() -> new BizException(GeneralParameterErrorCode.INVALID_PARAMETER))
                        .getErrors(), errorCode)
                .build();
        log.error(response.getMessage());
        return new ResponseEntity<>(response, getHttpHeader(), httpStatus);
    }

    /**
     * 클라이언트에게 전달할 에러 정보 헤더를 설정합니다.
     *
     * @return 에러 응답 헤더
     */
    private static HttpHeaders getHttpHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");
        return responseHeaders;
    }

}