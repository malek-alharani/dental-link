package com.strawary.dental_link.dto

import play.api.libs.json.{Format, Json}

object PatientDto {
  implicit val patientFormat: Format[PatientDto] = Json.format[PatientDto]
}

case class PatientDto(
                       userId: Option[Long],
                       fullName: String,
                       email: Option[String],
                       phoneNumber: String,
                       profilePicture: Option[String],
                       age: Int,
                       gender: String,
                       location: String,
                       chronicConditions: List[String],
                       specialNeeds: List[String],
                       medicalReports: Option[String]
                     )

