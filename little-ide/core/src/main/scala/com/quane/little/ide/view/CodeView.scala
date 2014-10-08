package com.quane.little.ide.view

import com.quane.little.language.EvaluableCode

sealed trait CodeView[P <: CodeViewPresenter] extends View[P]

sealed trait CodeViewPresenter extends ViewPresenter

/** A view for a component representing an [[com.quane.little.language.EvaluableCode]].
  *
  * @author Sean Connolly
  */
trait EvaluableCodeView[P <: EvaluableCodeViewPresenter] extends CodeView[P]

/** A presenter for views representing an [[com.quane.little.language.EvaluableCode]].
  *
  * @author Sean Connolly
  */
trait EvaluableCodeViewPresenter extends CodeViewPresenter {

  /** Compile the presented data to an [[com.quane.little.language.EvaluableCode]].
    *
    * @return the compiled expression
    */
  def compile(): EvaluableCode

}