package com.quane.little.data

import com.escalatesoft.subcut.inject.NewBindingModule
import com.mongodb.casbah.MongoClient
import org.mockito.Mockito._

/** Provides dependency injection bindings for real services and repositories
  * backed by a mock database.
  *
  * @author Sean Connolly
  */
object MockDatabaseBindingModule extends NewBindingModule(module => {

  import module._

  module <~ DataBindingModule

  // Override the normal MongoDB client with a mock
  bind[MongoClient] toSingle {
    // TODO should we mock out the collections??
    mock(classOf[MongoClient])
  }

})
