package com.opencars.netgo.Notifications.repository;

import com.opencars.netgo.Notifications.entity.Notifications;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    List<Notifications> findByUsersContainsOrderByIdDesc(User user);

    int countByViewsContaining(User user);
    int countByUsersContaining(User user);

    @Modifying
    @Query("delete FROM Notifications r where datediff(:current, r.date) > 15")
    void deleteNotifications(LocalDateTime current);
}
