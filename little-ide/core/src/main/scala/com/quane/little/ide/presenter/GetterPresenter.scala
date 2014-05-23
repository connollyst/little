package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GetterViewPresenter, GetterView}
import com.quane.little.language.Getter

/** Presenter for views representing a [[com.quane.little.language.Getter]].
  *
  * @author Sean Connolly
  */
class GetterPresenter(val view: GetterView)
  extends GetterViewPresenter {

  private var _name = ""

  view.registerViewPresenter(this)
  view.setName(_name)


  /** Initialize the get statement presenter.
    *
    * @param g the get statement
    * @return the initialized presenter
    */
  private[presenter] def initialize(g: Getter): GetterPresenter = {
    name = g.name
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(name: String): Unit = {
    _name = name
    view.setName(name)
  }

  override def onNameChange(name: String): Unit = _name = name

  override def compile(): Getter = new Getter(_name)

}