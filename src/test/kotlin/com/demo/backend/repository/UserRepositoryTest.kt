package com.demo.backend.repository

import com.demo.backend.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest @Autowired constructor(var userRepository: UserRepository) {

    companion object {
        private val log = LoggerFactory.getLogger(UserRepositoryTest::class.java)
        private val mapper = ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(JavaTimeModule())
    }

    @Test
    fun ut1001_add() {
        val user = User("test1", "repos", "test1@repos.you", "Admin", "MS", "1234test1")
        log.info("user: {}", mapper.writeValueAsString(user))
        val result = userRepository.save(user)
        log.info("result: {}", mapper.writeValueAsString(result))
    }

    @Test
    fun ut1002_getById() {
        val result = userRepository.findByIdOrNull(1)
        log.info("result: {}", mapper.writeValueAsString(result))
    }

    @Test
    fun ut1003_crud() {
        val user = User("test2", "repos", "test2@repos.you", "MS", "Admin", "1234test2")
        val result = userRepository.save(user)
        log.info("result: {}", mapper.writeValueAsString(result))

        val id: Long = result.id!!
        result.updateDts = LocalDateTime.now()
        result.role = "Admin"
        result.title = "MS"
        result.firstName = "Test2"
        result.password = "pass1234"

        userRepository.save(result)
        val result2 = userRepository.findByIdOrNull(id)
        log.info("result2: {}", mapper.writeValueAsString(result2))

        userRepository.deleteById(id)
        val result3 = userRepository.findByIdOrNull(id)
        log.info("result3: {}", result3)
    }

}