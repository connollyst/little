package com.quane.little.ide.view

import scala.collection.mutable.ListBuffer

/** A view in the little IDE.
  *
  * @tparam L the view listener type
  * @author Sean Connolly
  */
trait View[L <: ViewPresenter] {

  protected val viewPresenters = new ListBuffer[L]

  def addViewPresenter(l: L) = viewPresenters += l

  /** Remove this view from it's parent view.
    */
  def remove(): Unit

}

/** A view listener in the little IDE.
  *
  * @author Sean Connolly
  */
trait ViewPresenter