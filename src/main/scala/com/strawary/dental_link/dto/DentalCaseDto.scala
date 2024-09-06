package com.strawary.dental_link.dto

import play.api.libs.json.{Json, OFormat}

case class DentalCaseDto(
                          caseId: Option[Long],
                          caseType: String,
                          description: Option[String],
                          symptoms: String, // This field should be present
                          createdAt: Option[String],
                          updatedAt: Option[String],
                          patientId: Long
                        )

object DentalCaseDto {
  implicit val dentalCaseFormat: OFormat[DentalCaseDto] = Json.format[DentalCaseDto]
}

