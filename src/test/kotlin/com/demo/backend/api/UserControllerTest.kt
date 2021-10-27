package com.demo.backend.api

import com.demo.backend.entity.User
import com.demo.backend.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@ActiveProfiles("test")
@WebMvcTest
class UserControllerTest @Autowired constructor(val mockMvc: MockMvc, val mapper: ObjectMapper) {

    companion object {
        private val log = LoggerFactory.getLogger(UserControllerTest::class.java)
    }


    @MockkBean
    lateinit var userService: UserService

    @Test
    fun ut1001_findAll() {
        val user1 = User("apple", "fruit", "apple@like.you", "Ms", "Admin", "apple1234")
        val user2 = User("banana", "fruit", "banana@like.you", "Mr", "Manager", "banana1234")

        every { userService.findAll() } returns listOf(user1, user2)

        mockMvc.perform(get(UserController.BASE_URI).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].firstName").value(user1.firstName))
            .andExpect(jsonPath("\$.[0].email").value(user1.email))
            .andExpect(jsonPath("\$.[1].firstName").value(user2.firstName))
            .andExpect(jsonPath("\$.[1].email").value(user2.email))
            .andDo(MockMvcResultHandlers.print())
    }

    /**
     * @see UserController.add
     */
    @Test
    fun ut1002_add() {
        val scott = User("scott", "pines", "scott@pines.io", "Mr", "Admin", "scott")
        log.info("scott.hashCode: {}", scott.hashCode())
        val result = User(
            999, "scott", "pines", "scott@pines.io", "Mr", "Admin", "scott", LocalDateTime.now()
        )

        every { userService.save(scott) } returns result

        mockMvc.perform(
            post(UserController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(scott))
        ).andExpect(status().isCreated)
            .andExpect(header().stringValues("Location", "${UserController.BASE_URI}/${result.id}"))
            .andDo(MockMvcResultHandlers.print())
    }


}