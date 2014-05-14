package com.quane.little.data

import com.escalatesoft.subcut.inject.NewBindingModule
import com.mongodb.casbah.MongoClient

/** Provides dependency injection bindings for real services and repositories
  * backed by an empedded MongoDB database.
  *
  * @author Sean Connolly
  */
object EmbeddedDatabaseBindingModule extends NewBindingModule(module => {

  import module._

  module <~ DataBindingModule

  // Override the normal MongoDB client with that of the embedded database
  bind[MongoClient] toSingle {
    // TODO the EmbeddedMongoDB should be inherently connected to this
    MongoClient("127.0.0.1", 12345)
  }

})
