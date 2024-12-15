package com.simsimbookstore.frontserver.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocalUserRequest {
    private Long id;
    private String name;
    private String mobile_number;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // 날짜 형식 지정
    private Date birthday;
    private Gender gender;
    private State state = State.ACTIVE;
    private LocalDateTime createdAt;
    private LocalDateTime latestLoginDate;

    private String loginId;
    private String password;

    private Long gradeId = 1l;

    private Long roleId = 1l;

    public enum State{
        ACTIVE,
        INACTIVE,
        SLEEP
    }

    public enum Gender{
        MALE,
        FEMALE
    }
}
