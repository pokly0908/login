package com.yankong.login.repository;

import com.yankong.login.entity.RefreshToken;
import com.yankong.login.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUsername(String username);

    void deleteByUsername(String username);
}
