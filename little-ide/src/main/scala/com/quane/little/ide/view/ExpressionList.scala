package com.quane.little.ide.view

import vaadin.scala.VerticalLayout
import vaadin.scala.CssLayout
import com.vaadin.event.dd.DropHandler
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.vaadin.event.dd.DragAndDropEvent
import vaadin.scala.Component
import com.quane.vaadin.scala.DroppableTarget

object ExpressionList {
  val Style = "l-step-list"
  val StyleSeparator = Style + "-separator"
}

class ExpressionList extends VerticalLayout {
  styleName = ExpressionList.Style
  spacing = false

  override def add[C <: Component](component: C): C = {
    super.add(component)
    super.add(new ExpressionListSeparator())
    component
  }

  override def addComponent[C <: Component](component: C): C = {
    add(component)
  }

  override def removeComponent(c: Component) {
    val index = componentIndex(c)
    //    val separator = component(index + 1);
    //    super.removeComponent(separator);
    super.removeComponent(c)
  }

  def contains(c: Component): Boolean = {
    componentIndex(c) != -1
  }

  def componentIndex(c: Component): Int = {
    p.getComponentIndex(c.p)
  }

  //  def component(index: Int): Component = {
  //    super.components[index]
  //  }

}

class ExpressionListSeparator extends DroppableTarget(new CssLayout) {

  component.styleName = ExpressionList.StyleSeparator
  dropHandler = new DropHandler() {

    override def getAcceptCriterion(): AcceptCriterion = {
      // TODO only accept appropriate Little components
      return AcceptAll.get();
    }

    override def drop(event: DragAndDropEvent): Unit = {
      val droppedStep = getDroppedStep(event)
      val list = getStepList
      if (list.contains(droppedStep)) {
        list.removeComponent(droppedStep)
      }
      val separator = ExpressionListSeparator.this
      val separatorIndex = list.componentIndex(separator)
      list.add(droppedStep, separatorIndex + 1)
    }

    def getStepList: ExpressionList = {
      val t = ExpressionListSeparator.this
      val p = t.parent
      p.asInstanceOf[ExpressionList]
    }

    def getDroppedStep(event: DragAndDropEvent): Expression = {
      val sourceComponent = event.getTransferable().getSourceComponent().asInstanceOf[Component]
      if (sourceComponent.isInstanceOf[Expression]) {
        // An existing step is being moved from elsewhere
        return sourceComponent.asInstanceOf[Expression]
      } else if (sourceComponent.isInstanceOf[ToolboxItem]) {
        // A new step is being dropped from the toolbox
        val sourceStep = sourceComponent.asInstanceOf[ToolboxItem]
        return sourceStep.getStep
      } else {
        throw new ClassCastException("Drop not supported for "
          + sourceComponent.getClass().getSimpleName())
      }
    }

  }
}