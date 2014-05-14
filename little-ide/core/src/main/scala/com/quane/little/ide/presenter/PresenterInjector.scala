package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.NewBindingModule
import com.quane.little.data.service._
import com.mongodb.casbah.MongoClient

object PresenterInjector extends NewBindingModule(module => {

  import module._

  // TODO move to the data layer
  bind[UserService] toModuleSingle { implicit module => new MongoUserService(MongoClient())}
  bind[FunctionService] toModuleSingle { implicit module => new MongoFunctionService(MongoClient())}
  bind[ListenerService] toModuleSingle { implicit module => new MongoListenerService(MongoClient())}
  bind[ExpressionService] toModuleSingle { implicit module => new MongoExpressionService()}
  bind[StatementService] toModuleSingle { implicit module => new MongoStatementService()}

  bind[PresenterFactory] toProvider { implicit module => new PresenterFactory}

})
