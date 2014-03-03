package com.quane.little.ide.view.html

import vaadin.scala.{ComponentContainer, Component}
import com.quane.little.ide.view.ViewPresenter


/** A base HTML component trait.
  *
  * @author Sean Connolly
  */
trait RemovableComponent
  extends Component {
  //  with ViewWithChildren[_] {

  def removeFromParent(): Option[ViewPresenter] = {
    parent match {
      case Some(c) => c match {
        case container: ComponentContainer => container.removeComponent(this)
        case _ => throw new IllegalAccessException("Cannot remove " + this + " from " + parent)
      }
      case None => // strange
    }
    //    super.removeFromParent()
    None
  }

}
