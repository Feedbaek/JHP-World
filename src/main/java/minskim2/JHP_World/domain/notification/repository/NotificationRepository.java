package minskim2.JHP_World.domain.notification.repository;

import minskim2.JHP_World.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.receiver.id = :userId and n.isRead = false order by n.createdDate desc")
    List<Notification> findUnreadNotifications(Long userId);
}
