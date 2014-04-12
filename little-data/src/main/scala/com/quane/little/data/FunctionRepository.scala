package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.quane.little.data.model.FunctionRecord

/** Provides storage and retrieval access to the repository of
  * [[FunctionRecord]] objects.
  *
  * @author Sean Connolly
  */
class FunctionRepository(collection: MongoCollection)
  extends MongoRepository[FunctionRecord](collection)