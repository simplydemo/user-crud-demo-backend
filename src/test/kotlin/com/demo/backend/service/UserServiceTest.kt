package com.demo.backend.service

import com.demo.backend.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@TestMethodOrder(MethodOrderer.MethodName::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest(@Autowired val userService: UserService, @Autowired val mapper: ObjectMapper) {

    companion object {
        private val log = LoggerFactory.getLogger(UserServiceTest::class.java)
    }

    @Test
    fun ut1000_checkInstances() {
        log.info("userService: {}", userService)
        log.info("mapper: {}", mapper)
    }

    @Test
    fun ut1001_add() {
        val user = User("apple", "fruit", "apple@like.you", "MS", "Admin", "apple1234")

        log.info("user: {}", mapper.writeValueAsString(user))
        val result = userService.save(user)
        log.info("result: {}", mapper.writeValueAsString(result))
        //userService.delete(result)
    }

    @Test
    fun ut1002_getById() {
        val user = userService.getById(4)
        log.info("user: {}", mapper.writeValueAsString(user))
        assertThat(user).isNotNull
    }

    @Test
    fun ut1003_findAll() {
        val users = userService.findAll()
        log.info("user: {}", mapper.writeValueAsString(users))
        assertThat(users).isNotEmpty
        assertThat(users.toList()).hasSizeGreaterThan(0)
    }


    @Test
    fun ut1004_crud() {
        val user = User.Builder()
            .firstName("pineapple")
            .lastName("Fruit")
            .email("pineapple@farm.com")
            .role("Guest")
            .title("MS")
            .usercode("123123")
            .build()

        val result = userService.save(user)
        val id: Long? = result.id
        log.info("Created user id: {}", id)
        assertThat(id).isGreaterThan(0)
        assertThat(result.email).isEqualTo(user.email)

        result.email = "pineapple@farmspace.com"
        result.title = "MS"
        val result2 = userService.save(result)
        assertThat(result2.id).isEqualTo(id)
        log.info("result2: {}", mapper.writeValueAsString(result2))

        assertThat(userService.getById(id)).isNotNull

        userService.delete(id!!)
        assertThat(userService.getById(id)).isNull()
    }


}