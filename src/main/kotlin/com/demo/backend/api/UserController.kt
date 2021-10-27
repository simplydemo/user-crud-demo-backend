package com.demo.backend.api

import com.demo.backend.entity.User
import com.demo.backend.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime

@Controller
class UserController @Autowired constructor(val userService: UserService) {

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
        const val BASE_URI = "/api/users"
    }

    @GetMapping(BASE_URI)
    fun findAll(): ResponseEntity<List<User>> {
        val users = userService.findAll()
        log.info("users.size: {}", users.size)
        return ResponseEntity.ok(users)
    }

    @GetMapping("$BASE_URI/query")
    fun findAllByQuery(
        @RequestParam(name = "id", required = false) id: Long?,
        @RequestParam(name = "firstName", required = false) firstName: String?,
        @RequestParam(name = "lastName", required = false) lastName: String?,
        @RequestParam(name = "email", required = false) email: String?,
        @RequestParam(name = "role", required = false) role: String?,
        @RequestParam(name = "title", required = false) title: String?
    ): ResponseEntity<List<User>> {
        val user = User.Builder().id(id).firstName(firstName).lastName(lastName)
            .email(email).role(role).title(title).build()
        val users = userService.findAllByMatcher(user)
        log.info("users.size: {}", users.size)
        return ResponseEntity.ok(users)
    }

    @GetMapping("$BASE_URI/{id}")
    fun get(@PathVariable(name = "id") id: Long): ResponseEntity<User> {
        val user = userService.findById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping(BASE_URI)
    fun add(@RequestBody user: User): ResponseEntity<String> {
        val result = userService.save(user)
        log.info("result: {}", result)
        val location = UriComponentsBuilder.newInstance()
            .path(BASE_URI)
            .pathSegment(result.id.toString()).build().toUri()
        return ResponseEntity.created(location).build<String>()
    }

    @PutMapping("$BASE_URI/{id}")
    fun modify(@PathVariable(name = "id") id: Long, @RequestBody user: User): ResponseEntity<User>? {
        val opt = userService.findById(id)
        return if (opt.isPresent) {
            log.info("updated id: {}", id)
            user.updateDts = LocalDateTime.now()
            ResponseEntity.ok(userService.save(user))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("$BASE_URI/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<Any> {
        val user = userService.findById(id)
        return if (user.isPresent) {
            log.info("deleted id: {}", id)
            userService.delete(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

}