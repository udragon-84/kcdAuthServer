package com.kcd.repository.user;

import com.kcd.repository.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailOrMobile(String encryptEmail, String encryptMobile);
    boolean existsByEmailOrMobile(String encryptEmail, String encryptMobile);
}