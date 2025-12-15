package com.sollge.auth.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_gen", allocationSize = 1)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 64)
    private String email;

    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
