package com.quane.little.data.repo

import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import com.quane.little.data.model.{HasId, MongoId}
import com.quane.little.tools.json.LittleJSON

/** An abstract MongoDB repository.
  *
  * @author Sean Connolly
  */
abstract class MongoRepository[I <: MongoId, T <: HasId[I] : Manifest](collection: MongoCollection) {

  private val little = new LittleJSON()

  def update(record: T) = {
    // TODO check id exists
    // TODO check name not taken
    insert(record)
  }

  def insert(record: T) = {
    val json = little.serialize(record)
    JSON.parse(json) match {
      case dbObject: DBObject =>
        val writeResult = collection += dbObject
        if (writeResult.getError != null) {
          throw new RuntimeException("Failed to write object to database: " + writeResult.getError)
        }
        dbObject._id match {
          case Some(id) => record.id = recordId(id)
          case None =>
            throw new RuntimeException("Expected DBObject was assigned an OID.")
        }
      case _ =>
        throw new RuntimeException("Expected DBObject for " + json)
    }
  }

  def find(id: Option[MongoId]): Option[T] = {
    id match {
      case Some(rid) => find(rid)
      case None => None
    }
  }

  def find(id: MongoId): Option[T] = {
    val query = MongoDBObject("_id" -> id.toObjectId)
    val result = collection.findOne(query)
    deserialize(result)
  }

  protected def recordId(oid: ObjectId): I

  protected def deserialize[A <% DBObject](o: Option[A]): Option[T] =
    o match {
      case Some(cursor) => Some(deserialize(cursor))
      case None => None
    }

  protected def deserialize[A <% DBObject](o: A): T = {
    val json = JSON.serialize(o)
    little.deserialize[T](json)
  }

  protected def deserializeList(cursor: MongoCursor): List[T] =
    cursor.map {
      o: DBObject => deserialize(o)
    }.toList

}
