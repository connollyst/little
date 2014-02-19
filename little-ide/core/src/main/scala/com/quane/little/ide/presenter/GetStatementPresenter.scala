package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GetStatementViewListener, GetStatementView}
import com.quane.little.language.{GetStatement, Scope}
import com.quane.little.language.memory.Pointer

/** Presenter for views representing a [[com.quane.little.language.GetStatement]]
  *
  * @author Sean Connolly
  */
class GetStatementPresenter[V <: GetStatementView](view: V)
  extends StatementPresenter
  with GetStatementViewListener {

  private var _name = ""

  view.addViewListener(this)
  view.setName(_name)

  private[presenter] def name_=(name: String): Unit = {
    _name = name
    view.setName(name)
  }

  override def nameChanged(name: String): Unit = _name = name

  override def compile(scope: Scope): GetStatement = {
    new GetStatement(new Pointer(scope, _name))
  }

}