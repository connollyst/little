package com.quane.little.ide.view.html

import vaadin.scala.{ComponentContainer, Component}


/** A base HTML component trait.
  *
  * @author Sean Connolly
  */
trait HtmlComponent
  extends Component {

  /** Remove this component from it's parent.
    */
  private[html] def removeFromParent(): Unit = {
    // TODO notify parent view that the child is being removed
    parent match {
      case Some(c) => c match {
        case container: ComponentContainer => container.removeComponent(this)
        case _ => throw new IllegalAccessException("Cannot remove " + this + " from " + parent)
      }
      case None => // strange
    }
  }

}
