package my.vehiclemarket.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "pesho@example.com", roles = {"USER", "ADMIN"})
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePageAnonymous() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(request -> {
                            request.setRemoteUser(null);
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("welcomeMessage", "Anonymous"));
    }

    @Test
    public void testAddVehicleForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/add-vehicle"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-vehicle"))
                .andExpect(MockMvcResultMatchers.model().attribute("title", "Add Vehicle"));
    }
}
