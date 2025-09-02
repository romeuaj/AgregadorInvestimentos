package romeu.jesus.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import romeu.jesus.agregadordeinvestimentos.controller.dto.CreateStockDto;
import romeu.jesus.agregadordeinvestimentos.entity.Stock;
import romeu.jesus.agregadordeinvestimentos.repository.StockRepository;

import java.util.List;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {
        var stock = new Stock(createStockDto.stockId(), createStockDto.description());
        stockRepository.save(stock);
    }

    public List<Stock> listStocks() {
        return stockRepository.findAll();
    }
}
