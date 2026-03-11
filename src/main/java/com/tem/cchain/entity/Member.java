package com.tem.cchain.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "members") // [수정 1] 테이블 이름을 'members'로 고정 (에러 방지용)
public class Member {
    
    @Id
    @Column(length = 100)
    private String email; 
    
    @Column(unique = true, nullable = false, length = 50)
    private String userid; 
    
    @Column(nullable = false)
    private String userpw; 
    
    // 실제 이더리움 입금 주소 (0x.....)
    @Column(name = "wallet_address") // [수정 2] DB 컬럼명을 snake_case 관례에 맞춤
    private String walletaddress; 
    
    // 주소 개인키 (주의: 실서비스에서는 반드시 암호화 필수로 처리해야 함!)
    @Column(name = "private_key")
    private String privateKey;
    
    // DB상의 OMT 잔고 (기본값 0 설정 권장)
    @Column(name = "omt_balance", precision = 38, scale = 18) // [수정 3] 이더리움 단위(18자리) 대응
    @Builder.Default
    private BigDecimal omtBalance = BigDecimal.ZERO;
    
    @Column(name = "verified_cases")
    @Builder.Default
    private Long verifiedCases = 0L;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        // 초기 잔고와 케이스가 null일 경우 0으로 세팅
        if (this.omtBalance == null) this.omtBalance = BigDecimal.ZERO;
        if (this.verifiedCases == null) this.verifiedCases = 0L;
    }
    
    // 기여도 상승 메서드
    public void incrementVerifiedCases() {
        this.verifiedCases = (this.verifiedCases == null ? 0 : this.verifiedCases) + 1;
    }
}