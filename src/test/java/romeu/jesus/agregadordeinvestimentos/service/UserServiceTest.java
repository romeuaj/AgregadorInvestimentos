package romeu.jesus.agregadordeinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import romeu.jesus.agregadordeinvestimentos.controller.dto.UpdateDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.UserDto;
import romeu.jesus.agregadordeinvestimentos.entity.User;
import romeu.jesus.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;


    @Nested
    class createUser{
        @Test
        @DisplayName("Should create a user with sucess")
        void shoulCreateAUserWithSucess(){
            //Arrange
            var user = new User(
                                    UUID.randomUUID(),
                                    "username",
                                    "email@email.com",
                                    "password",
                                    Instant.now(),
                                    null,
                                    null
                                );
            doReturn(user).when(userRepository).saveAndFlush(userArgumentCaptor.capture());
            var input = new UserDto(
                                        "username",
                                        "email@email.com",
                                        "password"
                                    );
            //Act
            var output = userService.creatUser(input);
            //Assert
            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUserName());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }
        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccure(){
            //Arrange
            doThrow(new RuntimeException()).when(userRepository).saveAndFlush(any());
            var input = new UserDto(
                    "username",
                    "email@email.com",
                    "password"
            );
            //Act & Assert
            assertThrows(RuntimeException.class, ()-> userService.creatUser(input));
        }
    }

    @Nested
    class getUserById{
        @Test
        @DisplayName("Should get user by id with sucess whem optional is present")
        void shouldGetUserByIdWithSucessWhenOptionalIsPresent(){
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null,
                    null
            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            //Act
            var output = userService.getUserById(user.getUserId().toString());
            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with sucess whem optional is empty")
        void shouldGetUserByIdWithSucessWhenOptionalIsEmpty(){
            //Arrange
            var id = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            //Act
            var output = userService.getUserById(id.toString());
            //Assert
            assertTrue(output.isEmpty());
            assertEquals(id, uuidArgumentCaptor.getValue());
        }

    }

    @Nested
    class getAllUsers{
        @Test
        @DisplayName("Shoul return all users with sucess")
        void shoulReturAllUsersWithSucces(){
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null,
                    null
            );
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();
            //Act
            var output = userService.getAllUsers();
            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }


    }

    @Nested
    class deleteById{
        @Test
        @DisplayName("Should Delete User With Sucess")
        void shouldDeleteUserWithSucess() {
            //Arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            //Act
            userService.deleteById(userId.toString());
            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));
            verify(userRepository,times(1)).existsById(idList.get(0));
            verify(userRepository,times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should not Delete User When user not exist")
        void shouldNotDeleteUserWhenUserNotExist() {
            //Arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            //Act
            userService.deleteById(userId.toString());
            //Assert

            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository,times(1))
                    .existsById(uuidArgumentCaptor.getValue());
            verify(userRepository,times(0))
                    .deleteById(any());
        }
    }

    @Nested
    class updateUserById{
        @Test
        @DisplayName("Should update user by id whem user exist and user name and password is filed")
        void shouldUpdateUserByIdWhemUserExistAndUserNameAndPasswwordIsFiled() {
            //Arrange

            var updateDto = new UpdateDto(
                            "newUserName",
                            "newPassword"
                            );

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null,
                    null
                    );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            //Act
            userService.updateUserById(user.getUserId().toString(), updateDto);

            //Assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateDto.username(), userCaptured.getUserName());
            assertEquals(updateDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1))
                    .save(userArgumentCaptor.getValue());

        }

        @Test
        @DisplayName("Should not update user by id whem user not exist")
        void shouldNotUpdateUserByIdWhemUserNotExist() {
            //Arrange

            var updateDto = new UpdateDto(
                    "newUserName",
                    "newPassword"
            );

            var userId = UUID.randomUUID();


            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());


            //Act
            userService.updateUserById(userId.toString(), updateDto);

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());



            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0))
                    .save(any());

        }
    }

}