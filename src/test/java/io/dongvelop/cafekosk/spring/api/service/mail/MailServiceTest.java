package io.dongvelop.cafekosk.spring.api.service.mail;

import io.dongvelop.cafekosk.spring.client.mail.MailSendClient;
import io.dongvelop.cafekosk.spring.domain.mail.MailSendHistory;
import io.dongvelop.cafekosk.spring.domain.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author 이동엽(Lee Dongyeop)
 * @date 2024. 04. 01
 * @description
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Spy  // : @Mock과 다르게, 실제로 동작. + 일부분만 Stubbing 할 수 있음.
//    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;
    @InjectMocks
    private MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트 - 순수 Mocking")
    void sendMail() {

        // given

        /*
         * 아래 주석 처리한 내용들은 Annotation으로 대체 가능
         */
//        final MailSendClient mailSendClient = mock(MailSendClient.class);
//        final MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);
//        final MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        /* MailSendClient를 @Mock으로 작성시 */
//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);

        /* MailSendClient를 @Spy로 작성시 */
        doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1))
                .save(any(MailSendHistory.class));
    }

}