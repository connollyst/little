package com.quane.little.ide.view

import com.google.common.base.Objects


/** A basic IDE view.
  *
  * @tparam L the view listener type
  * @author Sean Connolly
  */
trait View[L <: ViewPresenter] {

  private var _viewPresenter: Option[L] = None

  //  protected var _viewParent: Option[ViewWithChildren[_]] = None

  /** Register this view's presenter.
    *
    * @param presenter the view's presenter
    */
  def registerViewPresenter(presenter: L) = {
    _viewPresenter match {
      case None => _viewPresenter = Some(presenter)
      case Some(p) => throw new IllegalAccessException(
        "Cannot register multiple presenters for " + this
          + "; already presented by " + p
      )
    }
  }

  //  /** Register this view's parent.
  //    *
  //    * @param parent the view's parent
  //    */
  //  def registerViewParent(parent: ViewWithChildren[_]) = {
  //    _viewParent match {
  //      case None => _viewParent = Some(parent)
  //      case Some(p) => throw new IllegalAccessException(
  //        "Cannot register multiple parents for " + this
  //          + "; already a child of " + p
  //      )
  //    }
  //  }

  def presenter: L = {
    _viewPresenter match {
      case Some(p) => p
      case None => throw new IllegalAccessException(
        "Expected registered presenter for " + this
      )
    }
  }

  //  def parent: ViewWithChildren[_] = {
  //    _viewParent match {
  //      case Some(p) => p
  //      case None => throw new IllegalAccessException(
  //        "Expected registered parent for " + this
  //      )
  //    }
  //  }

  //  /** Remove this view from it's parent view.<br/>
  //    * The removed view's presenter is returned so it can be added to a new
  //    * parent, if necessary.
  //    *
  //    * @return the removed view's presenter
  //    */
  //  def removeFromParent(): Option[ViewPresenter] = {
  //    _viewParent match {
  //      case Some(parent) => parent.removeChild(this)
  //      case None => None
  //    }
  //  }

  override def toString: String = Objects.toStringHelper(getClass).toString

}

///** An IDE view which may contain child views.
//  *
//  * @author Sean Connolly
//  */
//trait ViewWithChildren[L <: ViewWithChildrenPresenter]
//  extends View[L] {
//
//  /** Remove the specified child view from this view.
//    *
//    * @param child the child view to remove
//    * @return the child's presenter, or [[scala.None]] if it could not be
//    *         retrieved
//    */
//  def removeChild(child: View[_]): Option[ViewPresenter] = {
//    _viewPresenter match {
//      case Some(presenter) => presenter.removeChild()
//      case None => None
//    }
//  }
//
//}

/** A basic IDE view presenter.
  *
  * @author Sean Connolly
  */
trait ViewPresenter

///** A presenter for an IDE view which may contain child views.
//  *
//  * @author Sean Connolly
//  */
//trait ViewWithChildrenPresenter
//  extends ViewPresenter {
//
//  /** Remove the child.
//    *
//    * @return the child's presenter, or [[scala.None]] if it could not be retrieved
//    */
//  def removeChild(): Option[ViewPresenter] // TODO what child?
//
//}