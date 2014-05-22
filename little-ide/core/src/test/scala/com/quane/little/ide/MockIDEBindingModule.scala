package com.quane.little.ide

import com.escalatesoft.subcut.inject.NewBindingModule
import com.quane.little.data.MockDataBindingModule

/**
 *
 *
 * @author Sean Connolly
 */
object MockIDEBindingModule extends NewBindingModule(module => {

  module <~ IDEBindingModule
  module <~ MockDataBindingModule

})
