package com.quane.little.ide.view

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.RecordId

/** The view for the menu used to add code to a program.
  *
  * @author Sean Connolly
  */
trait CodeMenuView extends View[CodeMenuViewPresenter] {

  def addCategory(codeCategory: CodeCategory): Unit

  def addMenuItem(codeCategory: CodeCategory, id: RecordId, name: String): Unit

  def addMenuItemDisabled(codeCategory: CodeCategory, id: RecordId, name: String): Unit

}

trait CodeMenuViewPresenter extends ViewPresenter