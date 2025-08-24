package romeu.jesus.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import romeu.jesus.agregadordeinvestimentos.controller.UpdateDto;
import romeu.jesus.agregadordeinvestimentos.controller.UserDto;
import romeu.jesus.agregadordeinvestimentos.entity.User;
import romeu.jesus.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UUID creatUser(UserDto userDto){
        //DTO -> ENTITY
       var entity = new User(
                UUID.randomUUID(),
                userDto.username(),
                userDto.email(),
                userDto.password(),
                Instant.now(),
                null
        );
        var userSaved = repository.saveAndFlush(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){
        return repository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);
        var userExists = repository.existsById(id);
        if(userExists)
            repository.deleteById(UUID.fromString(userId));

    }
    public void updateUserById(String userId, UpdateDto updateDto){
        var id = UUID.fromString(userId);
        var userEntity = repository.findById(id);
        if(userEntity.isPresent()) {
            var user = userEntity.get();
            if(updateDto.password() != null){
                user.setPassword(updateDto.password());
            }
            if(updateDto.username() != null){
                user.setUserName(updateDto.username());
            }



            repository.save(user);
        }




    }
}
