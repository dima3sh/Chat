package avtetika.com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Message {

    @Id
    @Column(name = "message_id")
    private UUID messageId;

    private String text;

    @Column(name = "is_edit")
    private Boolean isEdit;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
