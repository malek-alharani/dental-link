package com.strawary.dental_link.model

import com.strawary.dental_link.model.Role.{PATIENT, STUDENT}
import jakarta.persistence.{AttributeConverter, Column, Convert, Converter, Entity, EnumType, Enumerated, GeneratedValue, GenerationType, Id, Inheritance, InheritanceType}

import java.time.LocalDateTime


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var userId: Long = _

  @Column(nullable = false)
  var fullName: String = _

  @Column(nullable = true)
  var email: String = _

  @Column(nullable = false, unique = true)
  var phoneNumber: String = _

  @Convert(converter = classOf[RoleConverter])
  var role: Role = _

  @Column(nullable = true)
  var profilePicture: String = _

  @Column(nullable = true)
  var otpCode: String = _ // Ensure this field exists in your entity

  @Column(nullable = true)
  var otpExpiration: LocalDateTime = _ // Ensure this field exists

  @Column(nullable = false)
  var isVerified: Boolean = false
}

sealed trait Role

object Role {
  case object STUDENT extends Role
  case object PATIENT extends Role


  // Convert Role to String
  def toString(role: Role): String = {
    if (role == null) "UNKNOWN" // Handle null case by returning a default or null-safe value
    else role match {
      case STUDENT => "STUDENT"
      case PATIENT => "PATIENT"
    }
  }

  // Convert String to Role
  def fromString(role: String): Role = role match {
    case "STUDENT" => STUDENT
    case "PATIENT" => PATIENT
    case _ => throw new IllegalArgumentException(s"Unknown role: $role")
  }
}


@Converter(autoApply = true)
class RoleConverter extends AttributeConverter[Role, String] {

  // Convert Role to String when storing in the database
  override def convertToDatabaseColumn(role: Role): String = {
    if (role == null) null // Handle null input by returning null
    else role match {
      case Role.STUDENT => "STUDENT"
      case Role.PATIENT => "PATIENT"
      case _ => throw new IllegalArgumentException(s"Unknown role: $role")
    }
  }

  // Convert String from database to Role when reading from the database
  override def convertToEntityAttribute(roleString: String): Role = {
    if (roleString == null) null // Handle null input by returning null
    else roleString match {
      case "STUDENT" => Role.STUDENT
      case "PATIENT" => Role.PATIENT
      case _ => throw new IllegalArgumentException(s"Unknown role string: $roleString")
    }
  }
}