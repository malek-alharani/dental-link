package com.strawary.dental_link.service
import com.strawary.dental_link.dto.UserDto
import com.strawary.dental_link.model.{Role, User}
import com.strawary.dental_link.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

import scala.jdk.CollectionConverters._
import scala.jdk.OptionConverters._


@Service
class UserService @Autowired()(val userRepository: UserRepository) {

  private def mapToUserDto(user: User): UserDto = {
    UserDto(
      userId = Some(user.userId),
      fullName = user.fullName,
      email = Option(user.email),
      phoneNumber = user.phoneNumber,
      profilePicture = Option(user.profilePicture),
      otpCode = Option(user.otpCode),
      otpExpiration = Option(user.otpExpiration).map(_.toString),
      isVerified = user.isVerified
    )
  }

  private def mapToUserEntity(userDto: UserDto): User = {
    val user = new User
    user.fullName = userDto.fullName
    user.email = userDto.email.orNull
    user.phoneNumber = userDto.phoneNumber
    user.profilePicture = userDto.profilePicture.orNull
    user.otpCode = userDto.otpCode.orNull
    user.otpExpiration = userDto.otpExpiration.map(java.time.LocalDateTime.parse).orNull
    user.isVerified = userDto.isVerified
    user
  }

  def getAllUsers: List[UserDto] = {
    userRepository.findAll().asScala.toList.map(mapToUserDto)
  }

  def getUserById(id: Long): Option[UserDto] = {
    userRepository.findById(id).toScala.map(mapToUserDto)
  }

  def createUser(userDto: UserDto): UserDto = {
    val user = mapToUserEntity(userDto)
    val savedUser = userRepository.save(user)
    mapToUserDto(savedUser)
  }

  def updateUser(id: Long, updatedUserDto: UserDto): Option[UserDto] = {
    userRepository.findById(id).toScala.map { user =>
      user.fullName = updatedUserDto.fullName
      user.email = updatedUserDto.email.orNull
      user.phoneNumber = updatedUserDto.phoneNumber
      user.profilePicture = updatedUserDto.profilePicture.orNull
      user.otpCode = updatedUserDto.otpCode.orNull
      user.otpExpiration = updatedUserDto.otpExpiration.map(java.time.LocalDateTime.parse).orNull
      user.isVerified = updatedUserDto.isVerified
      val savedUser = userRepository.save(user)
      mapToUserDto(savedUser)
    }
  }

  def deleteUser(id: Long): Unit = {
    userRepository.deleteById(id)
  }
}
