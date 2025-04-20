package by.bsu.flightwise.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Passenger(
    val id: Long = 0,
    val name: String,
    val surname: String,
    val passportNumber: String,
    val birthDate: Date
) : Parcelable