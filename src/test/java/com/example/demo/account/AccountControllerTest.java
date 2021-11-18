package com.example.demo.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    Account vasyl = new Account(1L,"Vasyl", "Danylenko", "saifferela@gmail.com", LocalDate.of(1996, Month.OCTOBER, 24));
    List<Account> allAccount = List.of(vasyl);


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;

    @Test
    void getAccount() throws Exception{

        when(accountService.getAccount()).thenReturn(allAccount);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"id\":1,\"firstName\":\"Vasyl\"," +
                                "\"lastName\":\"Danylenko\"," +
                                "\"email\":\"saifferela@gmail.com\"," +
                                "\"dob\":\"1996-10-24\"," +
                                "\"age\":25}]")));
    }

    @Test
    void addAccount() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/").content(
                "{\"id\":1,\"firstName\":\"Vasyl\"," +
                "\"lastName\":\"Danylenko\"," +
                "\"email\":\"saifferela@gmail.com\"," +
                "\"dob\":\"1996-10-24\"," +
                "\"age\":25}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteAccount() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/1?firstName=DIMA"))
                .andExpect(status().isOk());
    }
}