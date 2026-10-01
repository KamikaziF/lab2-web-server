package es.unizar.webeng.lab2
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

data class TimeDTO(
    val time: LocalDateTime,
)

fun LocalDateTime.toDTO(): TimeDTO = TimeDTO(time = this)

interface TimeProvider {
    fun now(): LocalDateTime
}

@Service
class TimeService : TimeProvider {
    override fun now(): LocalDateTime = LocalDateTime.now()
}

@RestController
class TimeController(
    private val service: TimeProvider,
) {
    @GetMapping("/time")
    fun time(): TimeDTO = service.now().toDTO()
}
