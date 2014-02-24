package com.quane.little.ide.view.html

import vaadin.scala.{ComponentContainer, Component}


/** An HTML component trait.
  *
  * @author Sean Connolly
  */
trait HtmlComponent
  extends Component {

  /** Remove this component from it's parent.
    */
  def removeFromParent(): Unit = {
    // TODO notify parent view that the child is being removed
    parent match {
      case None =>
      // not attached to any parent
      case _ =>
        parent.get match {
          case container: ComponentContainer => container.removeComponent(this)
          case _ => throw new IllegalAccessException("Expected ")
        }
    }
  }

}
