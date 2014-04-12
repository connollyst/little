package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import com.quane.little.data.model.{RecordID, HasRecordID}
import com.quane.little.tools.json.LittleJSON

/** A MongoDB repository trait.
  *
  * @author Sean Connolly
  */
abstract class MongoRepository[T <: HasRecordID : Manifest](collection: MongoCollection) {

  private val little = new LittleJSON()

  def insert(record: T) = {
    val json = little.serialize(record)
    JSON.parse(json) match {
      case dbObject: DBObject =>
        val writeResult = collection += dbObject
        if (writeResult.getError != null) {
          throw new RuntimeException(
            "Failed to write object to database: " + writeResult.getError
          )
        }
        dbObject._id match {
          case Some(id) => record.id = RecordID(id)
          case None => throw new RuntimeException(
            "Expected DBObject was assigned an OID."
          )
        }
      case _ => throw new RuntimeException("Expected DBObject for " + json)
    }
  }

  def find(id: RecordID): Option[T] = {
    val query = MongoDBObject("_id" -> id.toObjectId)
    val result = collection.findOne(query)
    deserialize(result)
  }

  def deserialize[A <% DBObject](o: Option[A]): Option[T] =
    o match {
      case Some(cursor) =>
        val json = JSON.serialize(cursor)
        val record = little.deserialize[T](json)
        Some(record)
      case None =>
        None
    }

}
