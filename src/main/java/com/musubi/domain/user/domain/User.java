package com.musubi.domain.user.domain;


import com.musubi.domain.location.domain.CurrentLocation;
import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.domain.SafeArea;
import com.musubi.domain.user.type.SexType;
import com.musubi.global.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User extends BaseEntity { // 보호자

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

    private String provider;

    private String providerId;

    private String fcmToken;

    @OneToOne
    private Location location;

    @OneToOne(mappedBy = "user")
    private Guardian guardian;

    @OneToOne
    private CurrentLocation currentLocation;

    @OneToMany(mappedBy = "user")
    private List<SafeArea> safeAreas = new ArrayList<>();

    public void updateFcmDeviceToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public boolean validatePassword(String inputPassword) {
        return inputPassword.equals(this.password);
    }

    public void connectGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

}
