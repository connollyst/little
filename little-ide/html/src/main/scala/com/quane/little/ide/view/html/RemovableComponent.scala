package com.quane.little.ide.view.html

import com.quane.little.ide.view.ViewPresenter
import com.vaadin.ui.{ComponentContainer, Component}


/** A base HTML component trait.
  *
  * @author Sean Connolly
  */
trait RemovableComponent
  extends Component {
  //  with ViewWithChildren[_] {

  def removeFromParent(): Option[ViewPresenter] = {
    // TODO null check?
    getParent match {
      case container: ComponentContainer => container.removeComponent(this)
      case _ => throw new IllegalAccessException("Cannot remove " + this + " from " + getParent)
    }
    //    super.removeFromParent()
    None
  }

}
