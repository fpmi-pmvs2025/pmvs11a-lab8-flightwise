package by.bsu.flightwise.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.Date

@Parcelize
data class Flight(
    val id: Long,
    val airplaneId: Long,
    val fromAirportId: Long,
    val toAirportId: Long,
    val departureTime: @RawValue Date,
    val arrivalTime: @RawValue Date,
    val pricingRuleId: Long
) : Parcelable
