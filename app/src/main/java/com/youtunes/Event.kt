package com.youtunes

import java.time.LocalDate

data class Event (
    val ticketMasterUrl: String,
    val eventDate: LocalDate,
    val eventPlace: String,
    val eventCity: String
    )