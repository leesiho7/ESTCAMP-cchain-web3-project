package com.tem.cchain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "documents") // [수정 1] 테이블 이름을 'documents'로 고정
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id") // [추가] PK 명칭 구체화
    private Long id;
    
    @Column(nullable = false)
    private String titleCn;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contentCn; // 원문 중국어 데이터
    
    private String sourceName;
    
    @Column(columnDefinition = "TEXT")
    private String summary; 
    
    // [수정 2] String 대신 LocalDateTime을 사용하면 날짜 계산과 정렬이 훨씬 편해집니다.
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; 
    
    @ManyToOne(fetch = FetchType.LAZY) // [수정 3] 성능 최적화(지연 로딩) 필수
    @JoinColumn(name = "member_email") 
    private Member member; 
    
    @Builder.Default 
    @Column(length = 20)
    private String status = "PENDING";

    // 데이터 저장 전 자동으로 시간을 채워줍니다.
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDING";
    }
}