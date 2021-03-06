package com.quane.little.ide.view

import com.google.common.base.Objects


/** A basic IDE view.
  *
  * @tparam P the view listener type
  * @author Sean Connolly
  */
trait View[P <: ViewPresenter] {

  private var _viewPresenter: Option[P] = None

  /** Register this view's presenter.
    *
    * @param presenter the view's presenter
    */
  def registerViewPresenter(presenter: P) = {
    _viewPresenter match {
      case None => _viewPresenter = Some(presenter)
      case Some(p) => throw new IllegalAccessException(
        "Cannot register multiple presenters for " + this
          + "; already presented by " + p
      )
    }
  }

  def presenter: P = {
    _viewPresenter match {
      case Some(p) => p
      case None => throw new IllegalAccessException(
        "Expected registered presenter for " + this
      )
    }
  }

  override def toString: String = Objects.toStringHelper(getClass).toString

}

/** A basic IDE view presenter.
  *
  * @author Sean Connolly
  */
trait ViewPresenter