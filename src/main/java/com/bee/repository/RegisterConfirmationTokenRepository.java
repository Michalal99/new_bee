package com.bee.repository;

import com.bee.models.PasswordResetToken;
import com.bee.models.RegisterConfirmationToken;
import com.bee.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.stream.Stream;

@Repository
public interface RegisterConfirmationTokenRepository extends JpaRepository<RegisterConfirmationToken, Long> {
    RegisterConfirmationToken findByToken(String token);

    RegisterConfirmationToken findByUser(User user);

    Stream<RegisterConfirmationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from RegisterConfirmationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

    @Transactional
    @Modifying
    @Query("delete from RegisterConfirmationToken t where t.user =:user")
    void deleteByUser(@Param("user") User user);
}
