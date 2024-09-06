package com.strawary.dental_link.dto

import play.api.libs.json.{Json, OFormat}

case class StudentRequestDto(
                              fullName: String,
                              email: Option[String],
                              phoneNumber: String,
                              profilePicture: Option[String] = None,
                              university: String,
                              currentYear: Int,
                              specialization: Option[String],
                              coursesCurrent: List[String],
                              skills: List[String],
                              neededCases: List[String],
                              location: String
                            )

object StudentRequestDto {
  implicit val studentFormat: OFormat[StudentRequestDto] = Json.format[StudentRequestDto]
}

case class StudentResponseDto(
                               userId: Option[Long],
                               fullName: String,
                               email: Option[String],
                               phoneNumber: String,
                               profilePicture: Option[String],
                               university: String,
                               role: String,
                               currentYear: Int,
                               specialization: Option[String],
                               coursesCurrent: List[String],
                               skills: List[String],
                               neededCases: List[String],
                               location: String
                             )

object StudentResponseDto {
  implicit val studentFormat: OFormat[StudentResponseDto] = Json.format[StudentResponseDto]
}