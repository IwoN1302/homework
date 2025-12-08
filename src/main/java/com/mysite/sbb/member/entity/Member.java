package com.mysite.sbb.member.entity;

import com.mysite.sbb.audit.BaseTimeEntity;
import com.mysite.sbb.member.constant.Department;
import com.mysite.sbb.member.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;  // 아이디

    @Column(unique = true, nullable = false, length = 20)
    private String username; // 사용자 ID

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(unique = true, nullable = false, length = 50)
    private String email; // 이메일

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Department department; // 학과

    @Column(nullable = false)
    private Boolean active; // 등록 여부

}
