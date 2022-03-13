package emlakburada.service;

import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import emlakburada.model.Message;
import emlakburada.repository.MessageRepository;

@SpringBootTest
public class MessageTest {
	
	@InjectMocks
	private EmailMessage messageService;
	
	
	@Mock
	private MessageRepository messageRepository;
	
	@Test
	void getMessage() {
		
		Mockito
		.when(messageRepository.save(any()))
		.thenReturn(prepareMessage("asd"));
		
		
	}

	private Message prepareMessage(String content) {
	
			Message message = new Message();
			message.setTitle("Test-Title");
			message.setContent("Test-Content");
			
			return message;
		
	}
	

}
