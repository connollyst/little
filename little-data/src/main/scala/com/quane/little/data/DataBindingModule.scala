package com.quane.little.data

import com.escalatesoft.subcut.inject.NewBindingModule
import com.quane.little.data.service._
import com.mongodb.casbah.MongoClient

/** A [[com.escalatesoft.subcut.inject.BindingModule]] definition providing
  * dependency injection of data layer services, repositories, and databases.
  *
  * @author Sean Connolly
  */
object DataBindingModule extends NewBindingModule(module => {

  import module._

  bind[MongoClient] toSingle {
    MongoClient()
  }
  bind[UserService] toModuleSingle { implicit module => new MongoUserService}
  bind[FunctionService] toModuleSingle { implicit module => new MongoFunctionService}
  bind[ListenerService] toModuleSingle { implicit module => new MongoListenerService}
  bind[ExpressionService] toModuleSingle { implicit module => new BasicExpressionService}
  bind[StatementService] toModuleSingle { implicit module => new BasicStatementService}

})
