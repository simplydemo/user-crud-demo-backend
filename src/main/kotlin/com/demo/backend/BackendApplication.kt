package com.demo.backend

import org.h2.tools.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import java.sql.SQLException

@SpringBootApplication
class BackendApplication {

    @Profile("default")
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Throws(SQLException::class)
    fun h2Server(): Server? {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
    }

}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
