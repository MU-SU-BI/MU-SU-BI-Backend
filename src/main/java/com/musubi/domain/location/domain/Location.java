package com.musubi.domain.location.domain;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.musubi.domain.user.domain.User;
import com.musubi.global.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Locations")
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String coordinate; // x,y 좌표

    //@Column(nullable = false)
    //private String address; // 전체 주소

    @Column(nullable = false)
    private String district; // 읍,면,동 기준 분류

    //@OneToOne
    //@JoinColumn(name = "Users")
    //private User user;

}
