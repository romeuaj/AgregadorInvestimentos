package romeu.jesus.agregadordeinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import romeu.jesus.agregadordeinvestimentos.controller.dto.AccountDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.UpdateDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.UserDto;
import romeu.jesus.agregadordeinvestimentos.entity.Account;
import romeu.jesus.agregadordeinvestimentos.entity.BillingAddres;
import romeu.jesus.agregadordeinvestimentos.entity.User;
import romeu.jesus.agregadordeinvestimentos.repository.AccountRepository;
import romeu.jesus.agregadordeinvestimentos.repository.BillingAddressRepository;
import romeu.jesus.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository repository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID creatUser(UserDto userDto){
        //DTO -> ENTITY
       var entity = new User(
                UUID.randomUUID(),
                userDto.username(),
                userDto.email(),
                userDto.password(),
                Instant.now(),
                null,
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

    public void createAccount(String userId, AccountDto accountDto) {
        var user = repository.findById(UUID.fromString(userId)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO -> ENTITY

        var account = new Account(
                                    null, // erro era causado por inserção em um campo modelado como GenerationType.UUID
                                    user,
                                    null,
                                    accountDto.description(),
                                    new ArrayList<>()
                                );
        var accountCreated = accountRepository.save(account);
        var billidaddress = new BillingAddres(
                                    accountCreated.getAccountId(),
                                    account,
                                    accountDto.street(),
                                    accountDto.number()
                                );
        billingAddressRepository.save(billidaddress);

    }
}
