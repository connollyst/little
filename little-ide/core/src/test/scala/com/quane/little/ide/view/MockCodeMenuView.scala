package com.quane.little.ide.view

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.Id

object MockCodeMenuView {
  def apply(): MockCodeMenuView = new MockCodeMenuView
}

/** An implementation of [[com.quane.little.ide.view.CodeMenuView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockCodeMenuView extends CodeMenuView {

  override def addCategory(codeCategory: CodeCategory) = Unit

  override def addMenuItem[I <: Id](codeCategory: CodeCategory, id: I, name: String) = Unit

  override def addMenuItemDisabled[I <: Id](codeCategory: CodeCategory, id: I, name: String) = Unit

}