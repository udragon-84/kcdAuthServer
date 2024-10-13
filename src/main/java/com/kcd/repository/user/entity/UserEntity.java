package com.kcd.repository.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 (JPA 용)
@AllArgsConstructor // 모든 필드를 포함하는 생성자 (Builder 사용 시 필요)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="mobile")
    private String mobile;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="birthday")
    private String birthday;

    @Column(name="rrn7th")
    private String rrn7th;

    @Column(name="gender")
    private String gender;

    @Column(name="nationality")
    private String nationality;

    @Column(name="tsp")
    private String tsp;

    @Column(name="provider")
    private String provider;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // 등록일
}
