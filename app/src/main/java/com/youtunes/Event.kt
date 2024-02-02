package com.youtunes

import java.time.ZonedDateTime

data class Event (
    val ticketMasterUrl: String,
    val eventDate: ZonedDateTime,
    val eventPlace: String,
    val eventCity: String
    )