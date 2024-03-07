package site.devowo.apiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class APIServerApplication

fun main(args: Array<String>) {
    runApplication<APIServerApplication>(*args)
}
