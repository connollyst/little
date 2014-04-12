package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects
import org.bson.types.ObjectId

/** A record id object. Maps to a MongoDB id when serialized to JSON.
  *
  * @param oid the record's oid
  */
class RecordID(@JsonProperty("$oid") oid: String) {

  def toObjectId: ObjectId = new ObjectId(oid)

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("$oid", oid)
      .toString

}

object RecordID {

  def apply(oid: ObjectId): RecordID = new RecordID(oid.toStringMongod)

}