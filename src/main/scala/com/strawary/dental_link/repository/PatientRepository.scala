package com.strawary.dental_link.repository

import com.strawary.dental_link.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait PatientRepository extends JpaRepository[Patient, Long] {
  def findByLocation(location: String): List[Patient]
}

