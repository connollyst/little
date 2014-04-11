package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.quane.little.data.model.FunctionDefinitionRecord

/** Provides storage and retrieval access to the repository of
  * [[FunctionDefinitionRecord]] objects.
  *
  * @author Sean Connolly
  */
class FunctionDefinitionRepository(collection: MongoCollection)
  extends MongoRepository[FunctionDefinitionRecord](collection)