package com.seneca.wareagles

import io.jstach.jstache.JStachePath
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@JStachePath(prefix = "templates/", suffix = ".mustache")
@SpringBootApplication
class SenecaWarEaglesWebappApplication

fun main(args: Array<String>) {
    runApplication<SenecaWarEaglesWebappApplication>(*args)
}
