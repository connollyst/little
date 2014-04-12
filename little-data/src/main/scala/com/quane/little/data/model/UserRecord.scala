package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects

/** A database record for a user.
  *
  * @author Sean Connolly
  */
class UserRecord(val username: String, val firstname: String, val lastname: String)
  extends HasRecordID {

  @JsonProperty("_id")
  var id: RecordID = _

  def fullname = firstname + " " + lastname

  override def equals(other: Any): Boolean = other match {
    case that: UserRecord =>
      id == that.id &&
        username == that.username &&
        firstname == that.firstname &&
        lastname == that.lastname
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, username, firstname, lastname)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("username", username)
      .add("fullname", fullname)
      .toString


}
