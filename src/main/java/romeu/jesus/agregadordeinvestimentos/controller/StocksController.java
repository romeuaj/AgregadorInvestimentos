package romeu.jesus.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romeu.jesus.agregadordeinvestimentos.controller.dto.CreateStockDto;
import romeu.jesus.agregadordeinvestimentos.entity.Stock;
import romeu.jesus.agregadordeinvestimentos.entity.User;
import romeu.jesus.agregadordeinvestimentos.service.StockService;

import java.util.List;

@RestController
@RequestMapping("v1/stocks")
public class StocksController {
    private StockService sotckService;

    public StocksController(StockService sotckService) {
        this.sotckService = sotckService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto){
        sotckService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<Stock>> listStock(){

        return ResponseEntity.ok(sotckService.listStocks());
    }
}
