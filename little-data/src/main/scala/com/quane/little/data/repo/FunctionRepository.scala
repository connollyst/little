package com.quane.little.data.repo

import com.mongodb.casbah.Imports._
import com.quane.little.data.model.{FunctionRecord, UserRecord}

/** Provides storage and retrieval access to the repository of
  * [[com.quane.little.data.model.FunctionRecord]] objects.
  *
  * @author Sean Connolly
  */
class FunctionRepository(collection: MongoCollection)
  extends MongoRepository[FunctionRecord](collection) {

  def findByUser(user: UserRecord): List[FunctionRecord] = {
    val query = MongoDBObject("ownerId" -> user.id.toObjectId)
    val results = collection.find(query)
    deserializeList(results)
  }

  def findByUser(user: UserRecord, name: String): Option[FunctionRecord] = {
    val query = MongoDBObject("ownerId" -> user.id.toObjectId, "definition.name" -> name)
    val result = collection.findOne(query)
    deserialize(result)
  }

}