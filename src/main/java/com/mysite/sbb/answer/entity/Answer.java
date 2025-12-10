package com.mysite.sbb.answer.entity;

import com.mysite.sbb.audit.BaseEntity;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.resume.entity.Resume;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
// @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resume_answer")
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id; // 아이디

    @Column(columnDefinition = "TEXT")
    private String content; // 질문 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;
}
