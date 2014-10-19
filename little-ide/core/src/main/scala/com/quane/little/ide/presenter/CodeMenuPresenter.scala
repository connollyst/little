package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.{CodeCategory, CodeRecord}
import com.quane.little.data.service.CodeService
import com.quane.little.ide.view.{CodeMenuView, CodeMenuViewPresenter}

/** A presenter for views of the menu used to add code to a program.
  *
  * @author Sean Connolly
  */
class CodeMenuPresenter[C <: PresenterAcceptsCode](val view: CodeMenuView, context: C)(implicit val bindingModule: BindingModule)
  extends CodeMenuViewPresenter
  with Injectable {

  private val codeService = inject[CodeService]

  view.registerViewPresenter(this)

  CodeCategory.values foreach {
    category => view.addCategory(category)
  }
  codeService.allRecordsForUser("connollyst") foreach addItem

  private def addItem(record: CodeRecord): Unit =
    if (accepts(record)) {
      view.addMenuItem(record.category, record.id, record.name)
    } else {
      view.addMenuItemDisabled(record.category, record.id, record.name)
    }

  private def accepts(record: CodeRecord): Boolean =
    PresenterAccepts.accepts(context, record.code.returnType)

}
