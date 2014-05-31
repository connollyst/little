package com.quane.little.ide.presenter

import com.quane.little.ide.view.{EvaluableCodeViewPresenter, CodeMenuView, CodeMenuViewPresenter}
import com.escalatesoft.subcut.inject.BindingModule

/** A presenter for views of the menu used to add code to a program.
  *
  * @author Sean Connolly
  */
class CodeMenuPresenter(val view: CodeMenuView)(implicit val bindingModule: BindingModule)
  extends CodeMenuViewPresenter {

  private[presenter] def initialize(context: EvaluableCodeViewPresenter): CodeMenuPresenter = {
    // TODO initialize menu items
    this
  }

}
