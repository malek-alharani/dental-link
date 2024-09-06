package com.strawary.dental_link.dto

import play.api.libs.json.{Format, Json}

object UserDto {
  implicit val userFormat: Format[UserDto] = Json.format[UserDto]
}

case class UserDto(
                    userId: Option[Long],
                    fullName: String,
                    email: Option[String],
                    phoneNumber: String,
                    profilePicture: Option[String],
                    otpCode: Option[String],
                    otpExpiration: Option[String],
                    isVerified: Boolean
                  )



