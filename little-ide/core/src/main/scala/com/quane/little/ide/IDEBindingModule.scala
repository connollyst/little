package com.quane.little.ide

import com.escalatesoft.subcut.inject.NewBindingModule
import com.quane.little.ide.presenter.PresenterBindingModule
import com.quane.little.data.DataBindingModule

/** A [[com.escalatesoft.subcut.inject.BindingModule]] definition for use in the
  * IDE, bringing together all expected dependencies for injection.
  *
  * @author Sean Connolly
  */
object IDEBindingModule extends NewBindingModule(module => {

  module <~ DataBindingModule
  module <~ PresenterBindingModule

})
