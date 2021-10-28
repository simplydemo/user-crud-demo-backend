package simple

import com.demo.backend.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.slf4j.LoggerFactory

@TestMethodOrder(MethodOrderer.MethodName::class)
class UserBuilderTest {

    companion object {
        private val log = LoggerFactory.getLogger(UserBuilderTest::class.java)
        private val mapper = ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)

        fun json(obj: Any): String {
            return mapper.writeValueAsString(obj)
        }
    }

    @Test
    fun ut1001_build() {
        val user = User.Builder().firstName("apple").build()
        assertThat(user).isNotNull
        assertThat(user).hasFieldOrPropertyWithValue("firstName", "apple")
        assertThat(user).hasFieldOrPropertyWithValue("lastName", null)
        log.info("user: {}", json(user))
    }

    @Test
    fun ut1002_build() {
        val user = User.Builder()
            .id(-1)
            .firstName("apple")
            .lastName("fruit")
            .email("apple@fruit.com")
            .title("MR")
            .role("Admin")
            .usercode("a12345")
            .build()
        log.info("user: {}", json(user))
        assertThat(user).isNotNull
        assertThat(user).hasFieldOrPropertyWithValue("firstName", "apple")
        assertThat(user.id).isLessThan(1)
        assertThat(user.lastName).isEqualTo("fruit")
        assertThat(user.email).startsWith("apple@")
        assertThat(user.title).isEqualToIgnoringCase("mr")
        assertThat(user.role).isEqualToIgnoringCase("admin")
        // org.assertj.core.api.Assertions().fail("")
    }
}