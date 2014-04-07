package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects

class ID(@JsonProperty("$oid") oid: String) {

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("$oid", oid)
      .toString

}
