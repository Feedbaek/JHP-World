package minskim2.JHP_World.domain.notification.repository;

import minskim2.JHP_World.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.receiver.id = :userId and n.isRead = false order by n.createdDate desc")
    Page<Notification> findUnreadNotifications(Long userId, Pageable pageable);

    @Query("select exists(select 1 from Notification n where n.receiver.id = :userId and n.isRead = false) as isExists")
    boolean existsByReceiverIdAndIsReadFalse(Long userId);
}
