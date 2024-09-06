package com.strawary.dental_link.service

import com.strawary.dental_link.model.Patient
import com.strawary.dental_link.repository.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.jdk.OptionConverters.RichOptional

@Service
class PatientService @Autowired()(val patientRepository: PatientRepository) {

  def getAllPatients: List[Patient] = {
    patientRepository.findAll().asScala.toList
  }

  def getPatientById(id: Long): Option[Patient] = {
    patientRepository.findById(id).toScala
  }

  def createPatient(patient: Patient): Patient = {
    patientRepository.save(patient)
  }

  def updatePatient(id: Long, updatedPatient: Patient): Option[Patient] = {
    val patientOpt = patientRepository.findById(id)
    patientOpt.map(patient => {
      patient.age = updatedPatient.age
      patient.location = updatedPatient.location
      patientRepository.save(patient)
    }).toScala
  }

  def deletePatient(id: Long): Unit = {
    patientRepository.deleteById(id)
  }
}
