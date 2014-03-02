package com.quane.little.ide.view.html

import vaadin.scala.{Component, ComponentContainer}

/**
 *
 *
 * @author Sean Connolly
 */
trait CloseableComponent
  extends Component {

  private[html] def close(): Unit = {
    parent match {
      case Some(c) => c match {
        case container: ComponentContainer => container.removeComponent(this)
        case _ => throw new IllegalAccessException("Cannot remove " + this + " from " + parent)
      }
      case None => // strange
    }
  }

}
