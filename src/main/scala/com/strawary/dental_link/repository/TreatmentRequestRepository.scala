package com.strawary.dental_link.repository

import com.strawary.dental_link.model.{Status, Student, TreatmentRequest}
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait TreatmentRequestRepository extends JpaRepository[TreatmentRequest, Long] {
  def findByStudent(student: Student): List[TreatmentRequest]
  def findByStatus(status: Status): List[TreatmentRequest]
}
