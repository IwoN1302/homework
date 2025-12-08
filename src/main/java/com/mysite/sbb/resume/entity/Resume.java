package com.mysite.sbb.resume.entity;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.audit.BaseEntity;
import com.mysite.sbb.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id") // Changed column name from question_id to resume_id? Or keep DB schema same?
    // If I change column name, H2/DB might strictly need migration or ddl-auto.
    // Assuming ddl-auto=update. Safe to change.
    private Long id; // 아이디

    @Column(length = 200, nullable = false)
    private String subject; // 이력서 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 이력서 내용

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume", cascade = CascadeType.ALL) // mappedBy "resume" in Answer
                                                                                       // entity?
    // I need to update Answer entity to have "resume" field instead of "question".
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;
}
