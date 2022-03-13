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
import emlakburada.dto.request.BannerRequest;
import emlakburada.model.Address;
import emlakburada.model.Banner;
import emlakburada.repository.BannerRepository;

@SpringBootTest
class BannerServiceTest {

	@InjectMocks
	private BannerService bannerService;
	
	@Mock
	private BannerRepository bannerRepository;
	
	
	private static Address address = new Address();
	private static Address address2 = new Address();
	
	
	@BeforeEach
	void setup() {
		Mockito.when(bannerRepository.findAll()).thenReturn(prepareBannerList());
	}
	
	@Test
	void saveBannerTest() {

		bannerService.saveBanner(prepareBanner());
		
		Mockito.verify(bannerRepository).save(any());

	}
	
	private List<Banner> prepareBannerList() {
		
		List<Banner> bannerList = new ArrayList<Banner>();
		bannerList.add(new Banner(1,3000,"55555555",250,address));
		bannerList.add(new Banner(2,3072,"50505050",500,address2));
		return bannerList;
	}
	

	private BannerRequest prepareBanner() {
		
		return new BannerRequest(1,3000,"55555555",250, null);
		
	}
	
	
	
}
