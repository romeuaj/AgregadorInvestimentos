package romeu.jesus.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romeu.jesus.agregadordeinvestimentos.controller.dto.*;
import romeu.jesus.agregadordeinvestimentos.entity.User;
import romeu.jesus.agregadordeinvestimentos.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserControler {
    private final UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        var userId = userService.creatUser(userDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ListUserDto>> getAllUsers(){
        var users = userService.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        var user = userService.getUserById(userId);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable String userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser( @PathVariable String userId,
                                            @RequestBody UpdateDto updateDto){
        userService.updateUserById(userId, updateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accounts/{userId}")
    public ResponseEntity<Void> updateUser( @PathVariable String userId,
                                            @RequestBody AccountDto accountDto){
        userService.createAccount(userId, accountDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<List<AccountResponseDto>>listAccounts(@PathVariable String userId){
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

}
