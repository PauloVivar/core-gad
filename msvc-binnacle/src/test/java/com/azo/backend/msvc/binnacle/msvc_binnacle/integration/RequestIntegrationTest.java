// package com.azo.backend.msvc.binnacle.msvc_binnacle.integration;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.when;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.services.RequestService;

// import java.util.Optional;

// @SpringBootTest
// public class RequestIntegrationTest {
  // @Autowired
  // private RequestService requestService;

  // @Autowired
  // private RequestRepository requestRepository;

  // @MockBean
  // private UserClientRest userClientRest;

  // @Test
  // void testCreateAndRetrieveRequest() {
  //     // Arrange
  //     Request request = new Request();
  //     request.setCitizenId(1L);
  //     request.setCadastralCode("TEST123");

  //     User user = new User();
  //     user.setId(1L);
  //     user.setUsername("testUser");
  //     user.setEmail("test@example.com");

  //     when(userClientRest.detail(1L)).thenReturn(user);

  //     // Act
  //     RequestDto createdRequest = requestService.save(request);
  //     Optional<RequestDto> retrievedRequestOpt = requestService.findById(createdRequest.getId());

  //     // Assert
  //     assertTrue(retrievedRequestOpt.isPresent());
  //     RequestDto retrievedRequest = retrievedRequestOpt.get();
  //     assertEquals(createdRequest.getId(), retrievedRequest.getId());
  //     assertEquals("TEST123", retrievedRequest.getCadastralCode());
  //     assertEquals("testUser", retrievedRequest.getCitizenName());
  //     assertEquals("test@example.com", retrievedRequest.getCitizenEmail());

  //     // Clean up
  //     requestRepository.deleteById(createdRequest.getId());
  //   }
  
// }
