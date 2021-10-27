package com.demo.backend.service

import com.demo.backend.entity.User
import com.demo.backend.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example

import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService @Autowired constructor(val userRepository: UserRepository) {

    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)

        val findOneMatcher = ExampleMatcher.matching()
            .withMatcher("id", exact())

        val findAllMatcher = ExampleMatcher.matching()
            .withMatcher("id", exact())
            .withMatcher("firstName", startsWith())
            .withMatcher("lastName", startsWith())
            .withMatcher("email", contains())
            .withMatcher("role", ignoreCase())
            .withMatcher("title", ignoreCase())
    }

    fun getById(id: Long?): User? {
        return userRepository.findByIdOrNull(id)
    }

    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findAllByMatcher(user: User): List<User> {
        return userRepository.findAll(Example.of(user, findAllMatcher))
    }

    fun save(user: User): User {
        log.info("user.hashCode: {}", user.hashCode())
        return userRepository.save(user)
    }

    fun delete(id: Long) {
        userRepository.deleteById(id)
        // userRepository.delete(user)
    }

}