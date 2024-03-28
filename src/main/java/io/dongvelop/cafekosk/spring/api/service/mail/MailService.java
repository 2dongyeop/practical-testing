package io.dongvelop.cafekosk.spring.api.service.mail;

import io.dongvelop.cafekosk.spring.client.mail.MailSendClient;
import io.dongvelop.cafekosk.spring.domain.mail.MailSendHistory;
import io.dongvelop.cafekosk.spring.domain.mail.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 이동엽(Lee Dongyeop)
 * @date 2024. 03. 26
 * @description
 */
@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);

        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .subject(subject)
                    .content(content)
                    .build());
            return true;
        }

        return false;
    }
}
