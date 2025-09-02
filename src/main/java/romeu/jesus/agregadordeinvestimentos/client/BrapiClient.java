package romeu.jesus.agregadordeinvestimentos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import romeu.jesus.agregadordeinvestimentos.client.dto.BrapiResponseDto;

@FeignClient(
            name = "BrapiClient",
            url = "https://brapi.dev"
)
public interface BrapiClient {
    @GetMapping(value = "/api/quote/{stockId}")
    BrapiResponseDto getQuote(@RequestParam String token,
                              @PathVariable("stockId") String stockId);
}
