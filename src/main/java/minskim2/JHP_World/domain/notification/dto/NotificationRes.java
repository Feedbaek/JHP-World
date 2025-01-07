package minskim2.JHP_World.domain.notification.dto;

import lombok.Builder;
import minskim2.JHP_World.domain.notification.entity.Notification;

public class NotificationRes {

    @Builder
    public record GetRes(
            Long id,
            Long postId,
            String message,
            String createdAt
    ) {
        public static GetRes from(Notification notification) {
            return GetRes.builder()
                    .id(notification.getId())
                    .postId(notification.getPost().getId())
                    .message(notification.getMessage())
                    .createdAt(notification.getCreatedDate().toString())
                    .build();
        }
    }
}
