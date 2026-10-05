package es.unizar.webeng.lab2

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class TimeDTO(
    val time: LocalDateTime,
)

fun LocalDateTime.toDTO(): TimeDTO = TimeDTO(time = this)

interface TimeProvider {
    // Si no se especifica zona, usa la del sistema por defecto
    fun now(zone: ZoneId = ZoneId.systemDefault()): LocalDateTime
}

@Service
class TimeService : TimeProvider {
    override fun now(zone: ZoneId): LocalDateTime = ZonedDateTime.now(zone).toLocalDateTime()
}

@RestController
class TimeController(
    private val service: TimeProvider,
) {
    @GetMapping("/time")
    fun time(
        @RequestParam(required = false) zone: ZoneId?,
    ): TimeDTO {
        val targetZone = zone ?: ZoneId.systemDefault()
        return service.now(targetZone).toDTO()
    }
}
