package com.strawary.dental_link.service

import com.strawary.dental_link.dto.DentalCaseDto
import com.strawary.dental_link.model.{CaseType, DentalCase}
import com.strawary.dental_link.repository.DentalCaseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.jdk.OptionConverters.RichOptional

@Service
class DentalCaseService @Autowired()(val dentalCaseRepository: DentalCaseRepository) {

  def getAllDentalCases: List[DentalCaseDto] = {
    dentalCaseRepository.findAll().asScala.toList.map(mapToDentalCaseDto)
  }

  def getDentalCaseById(id: Long): Option[DentalCaseDto] = {
    dentalCaseRepository.findById(id).toScala.map(mapToDentalCaseDto)
  }

  def createDentalCase(dentalCaseDto: DentalCaseDto): DentalCaseDto = {
    val dentalCase = mapToDentalCaseEntity(dentalCaseDto)
    val savedDentalCase = dentalCaseRepository.save(dentalCase)
    mapToDentalCaseDto(savedDentalCase)
  }

  def updateDentalCase(id: Long, updatedDentalCaseDto: DentalCaseDto): Option[DentalCaseDto] = {
    dentalCaseRepository.findById(id).toScala.map { dentalCase =>
      dentalCase.caseType = CaseType.fromString(updatedDentalCaseDto.caseType) // Use CaseType.fromString here
      dentalCase.description = updatedDentalCaseDto.description.orNull
      dentalCase.symptoms = updatedDentalCaseDto.symptoms
      dentalCase.createdAt = updatedDentalCaseDto.createdAt.map(java.time.LocalDateTime.parse).orNull
      dentalCase.updatedAt = updatedDentalCaseDto.updatedAt.map(java.time.LocalDateTime.parse).orNull
      val savedDentalCase = dentalCaseRepository.save(dentalCase)
      mapToDentalCaseDto(savedDentalCase)
    }
  }

  def deleteDentalCase(id: Long): Unit = {
    dentalCaseRepository.deleteById(id)
  }

  private def mapToDentalCaseDto(dentalCase: DentalCase): DentalCaseDto = {
    DentalCaseDto(
      caseId = Some(dentalCase.caseId),
      caseType = CaseType.toString(dentalCase.caseType),
      description = Option(dentalCase.description),
      symptoms = dentalCase.symptoms, // Make sure this field is present
      createdAt = Option(dentalCase.createdAt).map(_.toString),
      updatedAt = Option(dentalCase.updatedAt).map(_.toString),
      patientId = dentalCase.patient.userId
    )
  }

  private def mapToDentalCaseEntity(dentalCaseDto: DentalCaseDto): DentalCase = {
    val dentalCase = new DentalCase
    dentalCase.caseType = CaseType.fromString(dentalCaseDto.caseType)
    dentalCase.description = dentalCaseDto.description.orNull
    dentalCase.symptoms = dentalCaseDto.symptoms // Make sure this field is present
    dentalCase.createdAt = dentalCaseDto.createdAt.map(java.time.LocalDateTime.parse).orNull
    dentalCase.updatedAt = dentalCaseDto.updatedAt.map(java.time.LocalDateTime.parse).orNull
    dentalCase
  }

}
