package com.musubi.domain.user.dao;

import com.musubi.domain.user.domain.Guardian;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    Optional<Guardian> findByEmail(String email);

    Optional<Guardian> findByPhoneNumber(String phoneNumber);

    Optional<Guardian> findByNickname(String nickname);

    Optional<Guardian> findByUserId(Long userId);
}
