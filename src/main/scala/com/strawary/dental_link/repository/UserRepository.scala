package com.strawary.dental_link.repository

import com.strawary.dental_link.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait UserRepository extends JpaRepository[User, Long] {
  def findByPhoneNumber(phoneNumber: String): Option[User]
}