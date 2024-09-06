package com.strawary.dental_link.dto

import play.api.libs.json.{Format, Json}

object TreatmentRequestDto {
  implicit val treatmentRequestFormat: Format[TreatmentRequestDto] = Json.format[TreatmentRequestDto]
}

case class TreatmentRequestDto(
                                requestId: Option[Long],
                                caseId: Long,
                                studentId: Long,
                                status: String,
                                appointmentDate: Option[String],
                                createdAt: Option[String],
                                updatedAt: Option[String]
                              )

