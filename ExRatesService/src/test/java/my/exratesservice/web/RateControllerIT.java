package my.exratesservice.web;

import my.exratesservice.model.entity.RateEntity;
import my.exratesservice.service.RateService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RateControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RateService rateService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGetAllRates() throws Exception {

        RateEntity usdRate = new RateEntity("ZZZ", 1.2);
        RateEntity eurRate = new RateEntity("QQQ", 0.85);
        rateService.saveRate(usdRate);
        rateService.saveRate(eurRate);

        ResultActions response = mockMvc.perform(get("/rates"));

        response.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[?(@.currency == 'ZZZ')]").exists())  // Check if USD is present
                .andExpect(jsonPath("$[?(@.currency == 'QQQ')]").exists())  // Check if EUR is present
                .andExpect(jsonPath("$[?(@.currency == 'ZZZ')].rate").value(1.2))  // Verify USD rate
                .andExpect(jsonPath("$[?(@.currency == 'QQQ')].rate").value(0.85));  // Verify EUR rate
    }


    @Test
    public void testSaveRate() throws Exception {

        String rateJson = "{\"currency\": \"USD\", \"rate\": 1.2}";

        ResultActions response = mockMvc.perform(post("/rates")
                .contentType("application/json")
                .content(rateJson));

        response.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.rate").value(1.2));
    }

    @Test
    public void testDeleteRate() throws Exception {
        // Given
        RateEntity rate = new RateEntity();
        rate.setCurrency("USD");
        rate.setRate(1.2);
        rate = rateService.saveRate(rate);

        ResultActions response = mockMvc.perform(delete("/rates/" + rate.getId()));

        response.andExpect(status().isOk());
    }
}
