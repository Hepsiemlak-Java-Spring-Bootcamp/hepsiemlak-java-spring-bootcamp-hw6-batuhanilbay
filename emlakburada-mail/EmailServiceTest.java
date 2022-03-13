package emlakburada;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import emlakburada.config.EmailConfig;
import emlakburada.repository.EmailRepository;
import emlakburada.service.EmailService;

@SpringBootTest
public class EmailServiceTest {
	
	@InjectMocks
	private EmailService emailService;
	
	@Mock
	private EmailConfig emailConfig;
	
	@Mock 
	private EmailRepository repository;
	
	@Test
	void sendMessageTest() {
	
		Mockito
		.when(emailConfig.getSmtpServer()).thenReturn("test_server");
		Mockito
		.when(emailConfig.getPassword()).thenReturn("123456");
		Mockito
		.when(emailConfig.getSubject()).thenReturn("Konusuz");
		Mockito
		.when(emailConfig.getFrom()).thenReturn("hepsiemlak@gmail.com");
		Mockito
		.when(emailConfig.getSmtpPort()).thenReturn("1234");
		
		emailService.send("test@gmail.com");
	}
	
	
	

}
