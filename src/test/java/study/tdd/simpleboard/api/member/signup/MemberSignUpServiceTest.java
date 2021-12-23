package study.tdd.simpleboard.api.member.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@DisplayName("회원 가입 테스트")
public class MemberSignUpServiceTest {

    MemberRepository memberRepository = mock(MemberRepository.class);

    MemberSignUpService memberSignUpService = new MemberSignUpService(memberRepository);

    @Test
    public void createMemberService() {
        assertThat(memberSignUpService).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname1234:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("모든 조건을 통과시키고 회원가입을 성공한다.")
    public void saveMember(String nickname, String password, String memberEmail) {
        assertThat(memberSignUpService.saveMember(nickname, password, memberEmail)).isInstanceOf(Member.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname:password:pursue503@naver.com", "Informix:q1w2e3:pursue503@naver.com", "JaeHyun:qwerty123:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("모든 파라매터가 null 이 아닐 때 True 반환을 확인한다.")
    public void validateSignUpParam(String nickname, String password, String memberEmail) {
        assertThat(memberSignUpService.validateSignUpParam(nickname, password, memberEmail)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname", "Informix", "JaeHyun"})
    @DisplayName("파라매터가 1개 이상 비어 있을 때 False 를 반환하는 것을 확인한다.")
    public void whenOneParameterIsNull(String nickname) {
        assertThat(memberSignUpService.validateSignUpParam(nickname, null, null)).isFalse();
    }

    @Nested
    @DisplayName("닉네임 검증 테스트")
    class ValidNickNameTest {

        @ParameterizedTest
        @ValueSource(strings = {"nickname123", "informix123", "JaeHyun111"})
        @DisplayName("닉네임이 최소 3글자 이상일경우 True 반환을 확인한다.")
        public void validateNicknameThreeLength(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"nickname1234", "informix1111", "JaeHyun123123"})
        @DisplayName("닉네임에 알파벳이 포함되어 있을때 True 를 반환한다.")
        public void validateNicknameEnglish(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"1341315", "18918!@$", "19510751!@#"})
        @DisplayName("닉네임에 알파벳이 포함되어 있지 않을 때 False 를 반환한다.")
        public void validateNicknameNotEnglish(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc13191", "informix9991", "JaeHyun123"})
        @DisplayName("닉네임에 숫자가 포함 되어있을 경우 True 를 반환한다.")
        public void validateNicknameNumber(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abcdefg", "informix", "JaeHyun"})
        @DisplayName("닉네임에 숫자가 포함 되어있지 않을 경우 False 를 반환한다.")
        public void validateNicknameNotNumber(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abcd", "wohaha"})
        @DisplayName("닉네임이 3글자 이상이고 알파벳이 포함 되어 있지만 숫자가 없을경우 False 를 반환한다.")
        public void meetsTwoCriteriaOneBlame(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"a1", "w1"})
        @DisplayName("알파벳과 숫자가 포함되어 있지만 3글자 미만인경우 False 를 반환한다.")
        public void meetsTwoCriteriaOneBlame2(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"nickname123", "dkfjasADFFKDSOj1141kfndofn123411"})
        @DisplayName("닉네임이 3글자이상, 알파벳 포함, 숫자 포함 일 경우 True 를 반환한다.")
        public void meetsAllCriteria(String nickname) {
            assertThat(memberSignUpService.validateNickname(nickname)).isTrue();
        }
    }

    @Nested
    @DisplayName("비밀번호 검증 테스트")
    class ValidPasswordTest {

        @ParameterizedTest
        @ValueSource(strings = {"abcd1234!A", "bbbb1111@#A"})
        @DisplayName("모든 조건을 만족할 때 (8자 이상, 대문자하나 포함, 특수문자 하나포함, 숫자 하나이상) True 를 반환한다.")
        public void success(String password) {
            assertThat(memberSignUpService.validatePassword(password)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc12!A", "vcbaa!#A"})
        @DisplayName("비밀번호 조건에서 길이조건을 만족하지 못할 때 False 를 반환한다.")
        public void meetTheLengthCondition(String password) {
            assertThat(memberSignUpService.validatePassword(password)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc123!!##", "bcaicjac!!@@"})
        @DisplayName("비밀번호 조건에서 대문자조건을 만족하지 못할 때 False 를 반환한다.")
        public void meetTheEnglishCondition(String password) {
            assertThat(memberSignUpService.validatePassword(password)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc123421A", "vaijvz11BB"})
        @DisplayName("비밀번호 조건에서 특수문자조건을 만족하지 못할 때 False 를 반환한다.")
        public void meetTheSpecialCharactersCondition(String password) {
            assertThat(memberSignUpService.validatePassword(password)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc!@#!BBA", "aaiw!@!#PPZZ"})
        @DisplayName("비밀번호 조건에서 숫자조건을 만족하지 못할 때 False 를 반환한다.")
        public void meetTheNumberCondition(String password) {
            assertThat(memberSignUpService.validatePassword(password)).isFalse();
        }
    }

    @Nested
    @DisplayName("닉네임 중복 체크 테스트")
    class CheckDuplicatedNicknameTest {

        @ParameterizedTest
        @ValueSource(strings = {"nickname123", "nicknames33"})
        @DisplayName("닉네임이 중복되어있으면 True 를 반환한다.")
        public void duplicatedNickname(String nickname) {
            given(memberRepository.existsByNickname(nickname)).willReturn(true);
            assertThat(memberSignUpService.checkDuplicatedNickname(nickname)).isTrue();
        }

    }

}
