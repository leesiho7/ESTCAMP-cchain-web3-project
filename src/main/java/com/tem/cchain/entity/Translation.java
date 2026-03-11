package com.tem.cchain.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*; // 개별 import보다 깔끔하게 통합 가능
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "translations") // [수정 1] 테이블명 복수형 명시 (에러 방지 핵심)
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id") // [추가] PK 명칭을 구체화하면 나중에 조인할 때 편합니다.
    private Long id;
    
    @Column(columnDefinition = "TEXT", nullable = false) // [추가] 번역본은 필수이므로 nullable=false 권장
    private String contentKr;
    
    @ManyToOne(fetch = FetchType.LAZY) // [수정 2] 성능 최적화(지연 로딩)를 위해 필수 설정
    @JoinColumn(name = "doc_id")
    private Document document;
    
    @ManyToOne(fetch = FetchType.LAZY) // [수정 2] 성능 최적화
    @JoinColumn(name = "member_id") // [수정 3] Member 엔티티와 이름을 맞추는 것이 좋습니다.
    private Member user;
    
    @Column(length = 255) // [추가] 해시값 길이를 명시해주면 좋습니다.
    private String blockchainHash;
    
    @Column(name = "verified_at") // [추가] DB 컬럼명 관례(snake_case)에 맞춤
    private LocalDateTime verifiedAt; 
}