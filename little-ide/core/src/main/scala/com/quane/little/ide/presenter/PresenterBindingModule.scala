package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.NewBindingModule

/** A [[com.escalatesoft.subcut.inject.BindingModule]] definition for injecting
  * presenters and their dependencies.
  *
  * @author Sean Connolly
  */
object PresenterBindingModule extends NewBindingModule(module => {

  import module._

  bind[PresenterFactory] toProvider { implicit module => new PresenterFactory}

})
