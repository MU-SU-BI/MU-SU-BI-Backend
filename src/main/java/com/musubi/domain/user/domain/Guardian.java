package com.musubi.domain.user.domain;

import java.util.List;

import com.musubi.domain.community.domain.Post;
import com.musubi.domain.location.domain.CurrentLocation;
import com.musubi.domain.location.domain.Location;
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
import jakarta.persistence.OneToMany;
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

	private String provider;

	private String providerId;

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

	private String fcmToken;

	@OneToOne
	private Location location;

	public boolean notHasLocation() {
		return location == null;
	}

	public String getLocationDistrict() {
		if (location == null) {
			return null;
		}
		return location.getDistrict();
	}

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToMany(mappedBy = "guardianAuthor")
	private List<Post> posts;

	@OneToOne
	private CurrentLocation currentLocation;

	public void updateFcmDeviceToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public boolean validatePassword(String inputPassword) {
		return inputPassword.equals(password);
	}

	public void connectUser(User user) {
		this.user = user;
		user.connectGuardian(this);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setCurrentLocation(CurrentLocation currentLocation) {
		this.currentLocation = currentLocation;
	}
}
