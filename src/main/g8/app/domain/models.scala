package domain

import play.api.libs.json.Json

case class CreateUser(firstname: Option[String], lastname: String)
object CreateUser {
  implicit val createUserReads = Json.reads[CreateUser]
}

case class User(id: String, firstname: Option[String], lastname: String)
object User {
  implicit val userWrites = Json.writes[User]
}

case class UserUpdate(firstname: Option[String], lastname: Option[String])
object UserUpdate {
  implicit val userUpdateReads = Json.reads[UserUpdate]
}

case class UserError(error: String)
object UserError {
  implicit val userErrorWrites = Json.writes[UserError]
}
