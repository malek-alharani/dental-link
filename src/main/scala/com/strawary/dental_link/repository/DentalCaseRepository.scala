package com.strawary.dental_link.repository

import com.strawary.dental_link.model.{DentalCase, Patient}
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait DentalCaseRepository extends JpaRepository[DentalCase, Long] {
  def findByPatient(patient: Patient): List[DentalCase]
}
