package com.quane.little.ide.view

/** The view for a [[com.quane.little.language.Getter]] expression displayed in
  * the IDE.
  *
  * @author Sean Connolly
  */
trait GetterView extends CodeView[GetterViewPresenter] {

  /** Change the variable name in the view.
    *
    * @param name the new variable name
    */
  def setName(name: String): Unit

}

/** The presenter backing the [[GetterView]].
  *
  * @author Sean Connolly
  */
trait GetterViewPresenter extends CodeViewPresenter {

  /** Called when the variable name changes in the view.
    *
    * @param name the new variable name
    */
  def onNameChange(name: String): Unit

}