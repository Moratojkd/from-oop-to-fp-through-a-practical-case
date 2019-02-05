package version1

import model.User
import scalikejdbc._

import scala.util.Try

trait UserRepository {

  def findUserByEmail(email: String): Try[Option[User]]
}

class InMemoryUserRepository
(implicit session: DBSession) extends UserRepository {

  def findUserByEmail(email: String): Try[Option[User]] =
    Try(
      withSQL {
        select.from(User as User.u).where.eq(User.u.email, email)
      }.map(rs => User(rs)).single().apply())
}
