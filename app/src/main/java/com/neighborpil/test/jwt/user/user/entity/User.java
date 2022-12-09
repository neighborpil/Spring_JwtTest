package com.neighborpil.test.jwt.user.user.entity;

import com.neighborpil.test.jwt.config.jpa.entity.BaseEntity;
import com.neighborpil.test.jwt.config.jpa.entity.BaseTimeEntity;
import com.neighborpil.test.jwt.config.oauth2.entity.ProviderType;
import com.neighborpil.test.jwt.config.oauth2.entity.RoleType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private String username;
    private String password;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private String uuid;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    private LocalDateTime deletedAt;

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
