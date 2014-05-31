package com.quane.little.ide.view

import com.quane.little.data.model.CodeType.CodeType
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.RecordId

/** An implementation of [[com.quane.little.ide.view.CodeMenuView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockCodeMenuView extends CodeMenuView {

  override def addCategory(codeCategory: CodeCategory) = Unit

  override def addMenuItem(codeType: CodeType, codeCategory: CodeCategory, id: RecordId, name: String) = Unit

  override def addMenuItemDisabled(codeType: CodeType, codeCategory: CodeCategory, id: RecordId, name: String) = Unit

}