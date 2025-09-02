package romeu.jesus.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romeu.jesus.agregadordeinvestimentos.controller.dto.AccountStockResponseDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.AssociateAccountStockDto;
import romeu.jesus.agregadordeinvestimentos.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/stocks/{accountId}")
    public ResponseEntity<Void> associateStock(@PathVariable String accountId, @RequestBody AssociateAccountStockDto dto){
        accountService.AssociateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stocks/{accountId}")
    public ResponseEntity<List<AccountStockResponseDto>> listStock(@PathVariable String accountId){
        var accountList = accountService.listStock(accountId);
        return ResponseEntity.ok(accountList);
    }
}
