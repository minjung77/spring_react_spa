package com.example.zzyzzy.semiprojectv2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String userid;
    @Column(nullable = false)
    private String passwd;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    
    //insert, update 시 해당 컬럼 제외
    @CreationTimestamp//Timestamp와 동일한 효과
    @Column(insertable = false, updatable = false)
    private LocalDateTime regdate;
}
