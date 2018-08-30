package com.ionut.ciuta.posd1.fv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Functional verification for the requirements of scenario 2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class POSD2FV {
    @Autowired
    private MockMvc mvc;

    private final String root = "root";
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

    private String urlCreateRoleFor(String user) { return urlOf(user, "create_role"); }

    private String urlRoleRightFor(String user) { return urlOf(user, "role_rights"); }

    private String urlAssignRoleFor(String user) { return urlOf(user, "assign_role"); }

    private String urlAddRoleFor(String user) { return urlOf(user, "add_role"); }

    @Test
    public void test() throws Exception {
        // 1
        mvc.perform(post(urlCreateRoleFor(bob) + "?role=role1")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 2
        mvc.perform(post(urlCreateRoleFor(root) + "?role=role1")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 3
        mvc.perform(post(urlRoleRightFor(root) + "?role=role1&rights=r")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 4
        mvc.perform(post(urlAssignRoleFor(root) + "?role=role1&assignee=bob")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 5
        mvc.perform(post(urlCreateFor(alice) + "?name=/alice/cursuri.java&type=1&value=cursuri")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 6
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 7
        mvc.perform(post(urlAddRoleFor(bob) + "?role=role1&resource=/alice/cursuri.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 8
        mvc.perform(post(urlAddRoleFor(alice) + "?role=role1&resource=/alice/cursuri.java")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 9
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"cursuri\"}"));
        // 10
        mvc.perform(post(urlWriteFor(alice) + "?name=/alice/cursuri.java&value=cursuri2")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 11
        mvc.perform(post(urlWriteFor(bob) + "?name=/alice/cursuri.java&value=cursuri3")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 12
        mvc.perform(post(urlRoleRightFor(root) + "?role=role1&rights=w")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 13
        mvc.perform(post(urlWriteFor(bob) + "?name=/alice/cursuri.java&value=cursuri3")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 14
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"text\":\"Not Authorized\"}"));
        // 15
        mvc.perform(post(urlCreateRoleFor(root) + "?role=role2")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 16
        mvc.perform(post(urlRoleRightFor(root) + "?role=role2&rights=r")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 17
        mvc.perform(post(urlAssignRoleFor(root) + "?role=role2&assignee=bob")
                .content(root)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 18
        mvc.perform(post(urlAddRoleFor(alice) + "?role=role2&resource=/alice/cursuri.java")
                .content(alice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // 19
        mvc.perform(post(urlReadFor(bob) + "?name=/alice/cursuri.java")
                .content(bob)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
