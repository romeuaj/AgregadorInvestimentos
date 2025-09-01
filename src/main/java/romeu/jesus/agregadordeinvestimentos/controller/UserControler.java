package romeu.jesus.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romeu.jesus.agregadordeinvestimentos.controller.dto.AccountDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.UpdateDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.UserDto;
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
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userService.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser( @PathVariable("userId") String userId,
                                            @RequestBody UpdateDto updateDto){
        userService.updateUserById(userId, updateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> updateUser( @PathVariable("userId") String userId,
                                            @RequestBody AccountDto accountDto){
        userService.createAccount(userId, accountDto);
        return ResponseEntity.noContent().build();
    }


}
