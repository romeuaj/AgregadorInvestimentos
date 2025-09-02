package romeu.jesus.agregadordeinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import romeu.jesus.agregadordeinvestimentos.controller.dto.AccountStockResponseDto;
import romeu.jesus.agregadordeinvestimentos.controller.dto.AssociateAccountStockDto;
import romeu.jesus.agregadordeinvestimentos.entity.AccountStock;
import romeu.jesus.agregadordeinvestimentos.entity.AccountStockId;
import romeu.jesus.agregadordeinvestimentos.repository.AccountRepository;
import romeu.jesus.agregadordeinvestimentos.repository.AccountStockRepository;
import romeu.jesus.agregadordeinvestimentos.repository.StockRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private AccountStockRepository accountStockRepository;
    private StockRepository stockRepository;

    public AccountService(AccountRepository accountRepository, AccountStockRepository accountStockRepository, StockRepository stockRepository) {
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
        this.stockRepository = stockRepository;
    }

    public void AssociateStock(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO -> ENTITY
        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(id,
                                      account,
                                      stock,
                                      dto.quantity()
                                    );

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStock(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks().stream().map(as->
                new AccountStockResponseDto(as.getStock().getStockId(), as.getQuantity(), 0.0))
                .toList();

    }
}
