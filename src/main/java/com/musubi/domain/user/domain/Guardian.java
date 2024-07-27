package com.musubi.domain.user.domain;


import com.musubi.domain.location.domain.Location;
import com.musubi.domain.user.dto.ConnectionRequestDto;
import com.musubi.domain.user.type.SexType;
import com.musubi.global.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Guardian extends BaseEntity { // 보호자

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SexType sex;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String homeAddress;

    @OneToOne
    private Location location;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public boolean validatePassword(String inputPassword) {
        return inputPassword.equals(password);
    }

    public void connectUser(User user) {
        this.user = user;
        user.connectGuardian(this);
    }

}
