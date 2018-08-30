package com.ionut.ciuta.posd1.fv;

import com.ionut.ciuta.posd1.POSD1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Functional verification for the requirements of scenario 1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class POSD1FV {

    @Autowired
    private MockMvc mvc;

    private final String alice = "alice";
    private final String bob = "bob";

    private String urlOf(String user, String action) {
        String API_ROOT = "/sci/hw/resource/%s/%s";
        return String.format(API_ROOT, user, action);
    }

    private String urlCreateFor(String user) {
        return urlOf(user, "create");
    }

    private String urlReadFor(String user) {
        return urlOf(user, "read");
    }

    private String urlWriteFor(String user) {
        return urlOf(user, "write");
    }

    private String urlChangeFor(String user) {
        return urlOf(user, "rights");
    }

    @Test
    public void test() throws Exception {
        // 1
        mvc.perform(post(urlCreateFor(alice) + "?name=/bob/cursuri&value=&type=0")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 2
        mvc.perform(post(urlCreateFor(alice) + "?name=/alice/cursuri&value=&type=0")
            .content(alice)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        // 3
        mvc.perform(post(urlCreateFor(alice) + "?name=/alice/cursuri&value=&type=0")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"text\":\"Already Existing\"}"));
        // 4
        mvc.perform(post(urlCreateFor(alice) + "?name=/alice/cursuri&value=&type=1")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"text\":\"Already Existing\"}"));
        // 5
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 6
        mvc.perform(post(urlReadFor(alice) + "?name=/alice/cursuri")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 7
        mvc.perform(post(urlChangeFor(bob) + "?name=/alice/cursuri&rights=rw")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 8
        mvc.perform(post(urlChangeFor(alice) + "?name=/alice/cursuri&rights=rw")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 9
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"\"}"));
        // 10
        mvc.perform(post(urlCreateFor(alice) + "?name=/alice/cursuri/a.java&value=Test&type=0")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 11
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"a.java\"}"));
        // 12
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri/a.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"Test\"}"));
        // 13
        mvc.perform(post(urlChangeFor(alice) + "?name=/alice/cursuri/b.java&rights=rw")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"text\":\"Not Existing\"}"));
        // 14
        mvc.perform(post(urlChangeFor(alice) + "?name=/alice/cursuri/a.java&rights=")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 15
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri/a.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
    }
}
