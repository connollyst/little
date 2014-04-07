package com.quane.little.data.model

import com.google.common.base.Objects

/**
 *
 * @author Sean Connolly
 */
class User(val name: String) {

  var _id: ID = _

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", _id)
      .add("name", name)
      .toString

}
