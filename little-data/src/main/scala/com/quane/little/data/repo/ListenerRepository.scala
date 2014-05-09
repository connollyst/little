package com.quane.little.data.repo

import com.mongodb.casbah.Imports._
import com.quane.little.data.model.{UserRecord, ListenerRecord}

/** Provides storage and retrieval access to the repository of
  * [[com.quane.little.data.model.ListenerRecord]] objects.
  *
  * @author Sean Connolly
  */
class ListenerRepository(collection: MongoCollection)
  extends MongoRepository[ListenerRecord](collection) {

  def findByUser(user: UserRecord): List[ListenerRecord] = {
    val query = MongoDBObject("ownerId" -> user.id.toObjectId)
    val results = collection.find(query)
    deserializeList(results)
  }

}
