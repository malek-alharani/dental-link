package com.strawary.dental_link.model

import com.strawary.dental_link.model.Status.{APPROVED, COMPLETED, PENDING, REJECTED}
import jakarta.persistence._

import java.time.LocalDateTime

@Entity
class TreatmentRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var requestId: Long = _

  @ManyToOne
  @JoinColumn(name = "case_id", nullable = false)
  var dentalCase: DentalCase = _

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  var student: Student = _

  @Convert(converter = classOf[StatusConverter])
  var status: Status = _

  @Column(nullable = true)
  var appointmentDate: LocalDateTime = _

  @Column(nullable = false)
  var createdAt: LocalDateTime = LocalDateTime.now()

  @Column(nullable = false)
  var updatedAt: LocalDateTime = LocalDateTime.now()
}

sealed trait Status

object Status {
  case object PENDING extends Status

  case object APPROVED extends Status

  case object COMPLETED extends Status

  case object REJECTED extends Status

  def fromString(status: String): Status = status match {
    case "PENDING" => PENDING
    case "APPROVED" => APPROVED
    case "COMPLETED" => COMPLETED
    case "REJECTED" => REJECTED
    case _ => throw new IllegalArgumentException(s"Unknown status: $status")
  }

  def toString(status: Status): String = status match {
    case PENDING => "PENDING"
    case APPROVED => "APPROVED"
    case COMPLETED => "COMPLETED"
    case REJECTED => "REJECTED"
  }
}

@Converter(autoApply = true)
class StatusConverter extends AttributeConverter[Status, String] {
  // Convert Status to a String to store in the database
  override def convertToDatabaseColumn(status: Status): String = status match {
    case Status.PENDING => "PENDING"
    case Status.APPROVED => "APPROVED"
    case Status.COMPLETED => "COMPLETED"
    case Status.REJECTED => "REJECTED"
    case _ => throw new IllegalArgumentException(s"Unknown status: $status")
  }

  // Convert String from the database back to the Status
  override def convertToEntityAttribute(statusString: String): Status = statusString match {
    case "PENDING" => Status.PENDING
    case "APPROVED" => Status.APPROVED
    case "COMPLETED" => Status.COMPLETED
    case "REJECTED" => Status.REJECTED
    case _ => throw new IllegalArgumentException(s"Unknown status string: $statusString")
  }
}