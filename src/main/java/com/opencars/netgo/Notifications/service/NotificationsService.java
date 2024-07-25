package com.opencars.netgo.Notifications.service;

import com.opencars.netgo.Notifications.entity.Notifications;
import com.opencars.netgo.Notifications.repository.NotificationsRepository;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

@Service
@Transactional
public class NotificationsService {

    @Autowired
    NotificationsRepository notificationsRepository;

    public Optional<Notifications> getOne(Long id){
        return notificationsRepository.findById(id);
    }
    public Page<Notifications> getAll(Pageable pageable){
        return notificationsRepository.findAll(pageable);
    }

    public List<Notifications> getByUser(User user){
        return notificationsRepository.findByUsersContainsOrderByIdDesc(user);
    }
    public Notifications save(Notifications notification){
        return notificationsRepository.save(notification);
    }

    public Notifications newNotification(String title, String body, String route, SortedSet<User> users, Long idTicket){
        Notifications notification = new Notifications(
                title,
                body,
                "https://api.opencars.com.ar/api/show/logoopencars.png",
                route,
                users,
                idTicket
        );
        return notification;
    }

    public void updateUsers(Long id, SortedSet<User> users) {
        Notifications notification = this.getOne(id).get();
        notification.setUsers(users);
        this.save(notification);
    }

    public int getCountNotificationsNotViewed(User user){

        int countTotal = notificationsRepository.countByUsersContaining(user);
        int countViewed = notificationsRepository.countByViewsContaining(user);
        return countTotal - countViewed;

    }

    public void deleteNotifications(){

        LocalDateTime dateCurrent = LocalDateTime.now();
        notificationsRepository.deleteNotifications(dateCurrent);

    }

}
