package com.quane.little.ide.view

import com.quane.little.language.Code

/** A view for a component representing an [[com.quane.little.language.Code]].
  *
  * @author Sean Connolly
  */
trait CodeView[P <: CodeViewPresenter] extends View[P]

/** A presenter for views representing an [[com.quane.little.language.Code]].
  *
  * @author Sean Connolly
  */
trait CodeViewPresenter extends ViewPresenter {

  /** Compile the presented data to an [[com.quane.little.language.Code]].
    *
    * @return the compiled expression
    */
  def compile(): Code

}