package emlakburada.service;

import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import emlakburada.dto.request.UserRequest;
import emlakburada.model.User;
import emlakburada.model.enums.UserType;
import emlakburada.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setup() {

		Mockito
		.when(userRepository.findAll())
		.thenReturn(prepareMockUserList());
		

	}

	private List<User> prepareMockUserList() {
		List<User> userList = new ArrayList<User>();
		userList.add(new User());
		userList.add(new User(UserType.INDIVIDUAL, "batusko", "bthn@gmail.com"));
		return userList;
	}



	@Test
	void saveUserTest() {

		userService.saveUser(prepareUser());
		
		Mockito.verify(userRepository).save(any());

	}

	private UserRequest prepareUser() {
		return new UserRequest(1, UserType.INDIVIDUAL,"Batuhan","bthn@gmail.com", null, null);
	}
	/*
	
	@Test
	void getAllUsers() {
		
		Mockito.when(userRepository.findAll()).thenReturn(getUserList());
		List<UserResponse> userResponseList = userService.getAllUsers();
		assertNotEquals(0, userResponseList.size());
		assertThat(userResponseList.size()).isNotZero();
		
	}*/

	private List<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		userList.add((User) prepareMockUserList());
		userList.add((User) prepareMockUserList());
		
		return userList;
	}
	

	
	

}
