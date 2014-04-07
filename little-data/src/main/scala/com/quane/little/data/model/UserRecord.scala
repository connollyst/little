package com.quane.little.data.model

import com.google.common.base.Objects

/** A database record for a user.
  *
  * @author Sean Connolly
  */
class UserRecord(val username: String) {

  var _id: ID = _
  var firstname: String = _
  var lastname: String = _

  def fullname = firstname + " " + lastname

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", _id)
      .add("username", username)
      .add("fullname", fullname)
      .toString

}
