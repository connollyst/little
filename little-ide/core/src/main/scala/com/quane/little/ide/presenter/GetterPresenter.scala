package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GetterViewPresenter, GetStatementView}
import com.quane.little.language.GetStatement

/** Presenter for views representing a [[com.quane.little.language.GetStatement]].
  *
  * @author Sean Connolly
  */
class GetterPresenter[V <: GetStatementView](view: V)
  extends GetterViewPresenter {

  private var _name = ""

  view.registerViewPresenter(this)
  view.setName(_name)


  /** Initialize the get statement presenter.
    *
    * @param g the get statement
    * @return the initialized presenter
    */
  private[presenter] def initialize(g: GetStatement): GetterPresenter[V] = {
    name = g.name
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(name: String): Unit = {
    _name = name
    view.setName(name)
  }

  override def onNameChange(name: String): Unit = _name = name

  override def compile: GetStatement = new GetStatement(_name)

}