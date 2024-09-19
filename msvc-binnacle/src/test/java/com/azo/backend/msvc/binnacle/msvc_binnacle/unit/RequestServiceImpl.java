// package com.azo.backend.msvc.binnacle.msvc_binnacle.unit;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.mockStatic;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.azo.backend.msvc.binnacle.msvc_binnacle.clients.UserClientRest;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.User;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.RequestDto;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.dto.mapper.DtoMapperRequest;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.repositories.RequestRepository;
// import com.azo.backend.msvc.binnacle.msvc_binnacle.services.RequestService;

// class RequestServiceImpl {
  // @Mock
  // private RequestRepository repository;

  // @Mock
  // private UserClientRest userClientRest;

  // @InjectMocks
  // private RequestServiceImpl requestService;

  // @BeforeEach
  // void setUp() {
  //     MockitoAnnotations.openMocks(this);
  // }

  // @Test
  // void testFindById() {
  //     // Arrange
  //     Long requestId = 1L;
  //     Request request = new Request();
  //     request.setId(requestId);
  //     request.setCitizenId(2L);
  //     request.setCadastralCode("TEST123");

  //     RequestDto mappedDto = new RequestDto();
  //     mappedDto.setId(requestId);
  //     mappedDto.setCitizenId(2L);
  //     mappedDto.setCadastralCode("TEST123");

  //     User user = new User();
  //     user.setId(2L);
  //     user.setUsername("testUser");
  //     user.setEmail("test@example.com");

  //     when(repository.findById(requestId)).thenReturn(Optional.of(request));
  //     when(userClientRest.detail(2L)).thenReturn(user);

  //     // Mockear el DtoMapperRequest
  //     mockStatic(DtoMapperRequest.class);
  //     DtoMapperRequest dtoMapperMock = mock(DtoMapperRequest.class);
  //     when(DtoMapperRequest.builder()).thenReturn(dtoMapperMock);
  //     when(dtoMapperMock.setRequest(request)).thenReturn(dtoMapperMock);
  //     when(dtoMapperMock.build()).thenReturn(mappedDto);

  //     // Act
  //     //Optional<RequestDto> result = requestService.findById(requestId);
  //     Optional<RequestDto> result = ((RequestService) requestService).findById(requestId);

  //     // Assert
  //     assertTrue(result.isPresent());
  //     RequestDto requestDto = result.get();
  //     assertEquals(requestId, requestDto.getId());
  //     assertEquals("TEST123", requestDto.getCadastralCode());
  //     assertEquals("testUser", requestDto.getCitizenName());
  //     assertEquals("test@example.com", requestDto.getCitizenEmail());

  //     verify(repository).findById(requestId);
  //     verify(userClientRest).detail(2L);
  // }
  
// }
