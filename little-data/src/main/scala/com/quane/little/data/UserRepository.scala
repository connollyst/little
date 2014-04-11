package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.quane.little.data.model.UserRecord

/** Provides storage and retrieval access to the repository of [[UserRecord]]
  * objects.
  *
  * @author Sean Connolly
  */
class UserRepository(collection: MongoCollection)
  extends MongoRepository[UserRecord](collection)
