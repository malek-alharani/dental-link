package com.strawary.dental_link.service

import com.strawary.dental_link.dto.TreatmentRequestDto
import com.strawary.dental_link.model.{Status, TreatmentRequest}
import com.strawary.dental_link.repository.TreatmentRequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.jdk.OptionConverters.RichOptional

@Service
class TreatmentRequestService @Autowired()(val treatmentRequestRepository: TreatmentRequestRepository) {

  private def mapToTreatmentRequestDto(request: TreatmentRequest): TreatmentRequestDto = {
    TreatmentRequestDto(
      requestId = Some(request.requestId),
      caseId = request.dentalCase.caseId,
      studentId = request.student.userId,
      status = Status.toString(request.status), // Use Status.toString here
      appointmentDate = Option(request.appointmentDate).map(_.toString),
      createdAt = Option(request.createdAt).map(_.toString),
      updatedAt = Option(request.updatedAt).map(_.toString)
    )
  }

  private def mapToTreatmentRequestEntity(treatmentRequestDto: TreatmentRequestDto): TreatmentRequest = {
    val request = new TreatmentRequest
    request.status = Status.fromString(treatmentRequestDto.status) // Use Status.fromString here
    request.appointmentDate = treatmentRequestDto.appointmentDate.map(java.time.LocalDateTime.parse).orNull
    request
  }

  def getAllTreatmentRequests: List[TreatmentRequestDto] = {
    treatmentRequestRepository.findAll().asScala.toList.map(mapToTreatmentRequestDto)
  }

  def getTreatmentRequestById(id: Long): Option[TreatmentRequestDto] = {
    treatmentRequestRepository.findById(id).toScala.map(mapToTreatmentRequestDto)
  }

  def createTreatmentRequest(treatmentRequestDto: TreatmentRequestDto): TreatmentRequestDto = {
    val request = mapToTreatmentRequestEntity(treatmentRequestDto)
    val savedRequest = treatmentRequestRepository.save(request)
    mapToTreatmentRequestDto(savedRequest)
  }

  def updateTreatmentRequest(id: Long, updatedRequestDto: TreatmentRequestDto): Option[TreatmentRequestDto] = {
    treatmentRequestRepository.findById(id).toScala.map { request =>
      request.status = Status.fromString(updatedRequestDto.status) // Use Status.fromString here
      request.appointmentDate = updatedRequestDto.appointmentDate.map(java.time.LocalDateTime.parse).orNull
      val savedRequest = treatmentRequestRepository.save(request)
      mapToTreatmentRequestDto(savedRequest)
    }
  }

  def deleteTreatmentRequest(id: Long): Unit = {
    treatmentRequestRepository.deleteById(id)
  }
}
