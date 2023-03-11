package avtetika.com.chat.repository;

import avtetika.com.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ChatRepository extends PagingAndSortingRepository<Message, UUID> {

    Page<Message> findMessageByUserUserId(UUID userId, Pageable request);

    Page<Message> findAllByIsDelete(Boolean isDelete, Pageable request);
}
