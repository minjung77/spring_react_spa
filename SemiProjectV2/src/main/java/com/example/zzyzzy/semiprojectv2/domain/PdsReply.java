package com.example.zzyzzy.semiprojectv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pds_replys3")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PdsReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    @Column(nullable = false)
    private String comments;
    @Column(nullable = false)
    private String userid;
    @CreationTimestamp
    @Column(insertable = false)
    private LocalDateTime regdate;
    @Column(nullable = false)
    private int ref;

    @Column(nullable = false)
    private int rpno;


}
