package com.quane.little.data

import com.escalatesoft.subcut.inject.NewBindingModule
import com.quane.little.data.service.{ListenerService, FunctionService, UserService}

/** An extension of the normal data binding module which provides mock
  * implementations of the data services for testing higher level function.
  *
  * @author Sean Connolly
  */
object MockDataBindingModule extends NewBindingModule(module => {

  import module._

  module <~ DataBindingModule

  bind[UserService] toModuleSingle { implicit module => new MockUserService}
  bind[FunctionService] toModuleSingle { implicit module => new MockFunctionService}
  bind[ListenerService] toModuleSingle { implicit module => new MockListenerService}

})