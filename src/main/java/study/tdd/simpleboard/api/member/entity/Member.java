package study.tdd.simpleboard.api.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberEmail;
    private String nickname;
    private String password;

    @Builder
    public Member(String memberEmail, String nickname, String password) {
        this.memberEmail = memberEmail;
        this.nickname = nickname;
        this.password = password;
    }

}
