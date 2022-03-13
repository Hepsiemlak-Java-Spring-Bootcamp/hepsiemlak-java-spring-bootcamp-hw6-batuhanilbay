package emlakburada.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import emlakburada.dto.request.AdvertRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.exception.UserNotFoundException;
import emlakburada.model.Advert;
import emlakburada.model.User;
import emlakburada.model.enums.UserType;
import emlakburada.repository.AdvertRepository;
import emlakburada.repository.UserRepository;

@SpringBootTest
class AdvertServiceTest {

	@InjectMocks
	private AdvertService advertService;

	@Mock
	private AdvertRepository advertRepository;

	@Mock
	private UserRepository userRepository;


	
	
	
	@Test()
	void saveAdvertWithoutUserTest() {

		assertThrows(Exception.class, () -> {
			advertService.saveAdvert(prepareAdvertRequest());
		}, "İlan kaydedilemedi");

		

	}

	@Test
	void saveAdvertWithUserTest()  {
		AdvertRequest request = prepareAdvertRequest();
		Optional<User> user = Optional.of(prepareUser());
		Mockito
		.when(userRepository.findById(request.getUserId()))
		.thenReturn(user);
		Mockito
		.when(advertRepository.save(any()))
		.thenReturn(prepareAdvert("title"));
		
		
		

		assertDoesNotThrow(() -> {
			AdvertResponse response = advertService.saveAdvert(request);
			assertEquals("title", response.getTitle());
			
		});

	}

	@Test
	void saveAdvertWithUserTest1() throws Exception {
		AdvertRequest request = prepareAdvertRequest();

		Optional<User> user = Optional.of(prepareUser());
		Mockito
		.when(userRepository.findById(request.getUserId()))
		.thenReturn(user);
		
		Mockito
		.when(advertRepository.save(any()))
		.thenReturn(prepareAdvert(""));
		
	

		AdvertResponse response = advertService.saveAdvert(request);

		assertEquals("test-title", response.getTitle());
		

	}

	@BeforeEach
	void setup() {
		Mockito
		.when(advertRepository.findAll())
		.thenReturn(prepareAdvertList());
	
	}
	
	@Test
	void getAllAdvertsTest() {
		
		Mockito.when(advertRepository.findAll()).thenReturn(prepareAdvertList());

		List<AdvertResponse> responseList = advertService.getAllAdverts();

		assertNotEquals(0, responseList.size());

		assertThat(responseList.size()).isNotZero();

		for (AdvertResponse response : responseList) {
			assertThat(response.getAdvertNo()).isEqualTo(0);

			assertEquals(120000L, response.getPrice());

		}

	}

	private List<Advert> prepareAdvertList() {
		List<Advert> adverts = new ArrayList<Advert>();
		adverts.add(prepareAdvert("title1"));
		adverts.add(prepareAdvert("title2"));
		adverts.add(prepareAdvert("title3"));
		return adverts;
	}

	private Advert prepareAdvert(String title) {
		Advert advert = new Advert();
		advert.setAdvertNo(0);
		advert.setTitle(title);
		return advert;
	}

	private User prepareUser() {
		User user = new User(UserType.CORPORATE, "mock name", "email");
		return user;
	}

	private AdvertRequest prepareAdvertRequest() {
		AdvertRequest request = new AdvertRequest();
		request.setUserId(5);
		request.setTitle("test-title");
		request.setDuration(3);
		request.setPrice(120000L);
		return request;
	}

	@Test
	void getAdvertByAdvertIdTest() {


		Mockito
		.when(advertRepository.findById(0))
		.thenReturn(Optional.of(prepareAdvert("başlık")));
		

		AdvertResponse response = advertService.getAdvertByAdvertId(0);
		assertNotNull(response);
		assertEquals("başlık", response.getTitle());
		assertNotNull(response.getUser());

	}

	@Test
	void getAllFavoriteAdvertsByUserIdNotFoundUserTest() {

		assertThrows(UserNotFoundException.class, () -> {
			List<AdvertResponse> response = advertService.getAllFavoriteAdvertsByUserId(1);
			assertNull(response);
		}, "User Not Found");

	}

	@Test
	void getAllFavoriteAdvertsByUserIdTest() {

		User user = prepareUser();
		user.getFavoriIlanlar().add(prepareAdvert("title"));

		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

		assertDoesNotThrow(() -> {
			List<AdvertResponse> response = advertService.getAllFavoriteAdvertsByUserId(1);

			assertNotNull(user.getFavoriIlanlar());

			assertThat(user.getFavoriIlanlar().size()).isNotZero();

			assertNotNull(response);

			assertThat(response.size()).isNotZero();
		});

	}

}
