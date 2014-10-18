package com.quane.little.ide.view

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.RecordId

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

  override def addMenuItem(codeCategory: CodeCategory, id: RecordId, name: String) = Unit

  override def addMenuItemDisabled(codeCategory: CodeCategory, id: RecordId, name: String) = Unit

}