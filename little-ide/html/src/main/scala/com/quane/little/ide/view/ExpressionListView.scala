package com.quane.little.ide.view

import com.quane.little.ide.presenter.ExpressionPresenter
import com.quane.vaadin.scala.DroppableTarget

object ExpressionListView {
  val Style = "l-step-list"
  val StyleSeparator = Style + "-separator"
}

class ExpressionListView extends VerticalLayout {
  styleName = ExpressionListView.Style
  spacing = false

  override def add[C <: Component](component: C): C = {
    super.add(component)
    super.add(new ExpressionListViewSeparator())
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

class ExpressionListViewSeparator extends DroppableTarget(new CssLayout) {

  component.styleName = ExpressionListView.StyleSeparator
  dropHandler = new DropHandler() {

    override def getAcceptCriterion: AcceptCriterion = {
      // TODO only accept appropriate Little components
      AcceptAll.get()
    }

    override def drop(event: DragAndDropEvent): Unit = {
      val droppedStep = getDroppedStep(event)
      val list = getStepList
      if (list.contains(droppedStep)) {
        list.removeComponent(droppedStep)
      }
      val separator = ExpressionListViewSeparator.this
      val separatorIndex = list.componentIndex(separator)
      list.add(droppedStep, separatorIndex + 1)
    }

    def getStepList: ExpressionListView = {
      val t = ExpressionListViewSeparator.this
      val p = t.parent
      p.asInstanceOf[ExpressionListView]
    }

    def getDroppedStep(event: DragAndDropEvent): ExpressionView[ExpressionPresenter] = {
      val sourceComponent = event.getTransferable.getSourceComponent.asInstanceOf[Component]
      if (sourceComponent.isInstanceOf[ExpressionView[ExpressionPresenter]]) {
        // An existing step is being moved from elsewhere
        sourceComponent.asInstanceOf[ExpressionView[ExpressionPresenter]]
      } else if (sourceComponent.isInstanceOf[ToolboxItemView]) {
        // A new step is being dropped from the toolbox
        sourceComponent.asInstanceOf[ToolboxItemView].getStep
      } else {
        throw new ClassCastException("Drop not supported for "
          + sourceComponent.getClass.getSimpleName)
      }
    }

  }
}