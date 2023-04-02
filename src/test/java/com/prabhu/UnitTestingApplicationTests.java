package com.prabhu;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UnitTestingApplicationTests {
 
    @Autowired
    private MockMvc mockMvc;
 
    @Test
    public void whenGetUsers_thenReturnJsonArray() throws Exception {
        mockMvc.perform(get("/patient/get/3"))
          .andExpect(status().isOk());
        //  .andExpect(content().contentType(MediaType.APPLICATION_JSON));
         // .andExpect(jsonPath("$", hasSize(2)))
//          .andExpect(jsonPath("$[0].username", is("john.doe")))
//          .andExpect(jsonPath("$[1].username", is("jane.doe")));
    }
}
