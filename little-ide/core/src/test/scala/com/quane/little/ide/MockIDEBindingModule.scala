package com.quane.little.ide

import com.escalatesoft.subcut.inject.NewBindingModule
import com.quane.little.data.MockDataBindingModule

/** An extension of the normal IDE binding module which provides mock
 * implementations of the data services for testing higher level function.
 *
 * @author Sean Connolly
 */
object MockIDEBindingModule extends NewBindingModule(module => {

  module <~ IDEBindingModule
  module <~ MockDataBindingModule

})
