package io.dongvelop.cafekosk.spring.domain.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 이동엽(Lee Dongyeop)
 * @date 2024. 03. 28
 * @description
 */
@Repository
public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
