package com.quane.little.web.view

import vaadin.scala.VerticalLayout
import vaadin.scala.CssLayout
import com.quane.vaadin.scala.DragAndDropWrapper
import com.vaadin.event.dd.DropHandler
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.vaadin.event.dd.DragAndDropEvent
import vaadin.scala.Component

object ExpressionList {
  val Style = "l-step-list"
  val StyleSeparator = Style + "-separator";
}
class ExpressionList extends VerticalLayout {
  styleName = ExpressionList.Style
  spacing = false

  def contains(c: Component): Boolean = {
    componentIndex(c) != -1;
  }

  def componentIndex(c: Component): Int = {
    p.getComponentIndex(c.p)
  }

}
class ExpressionListSeparator extends DragAndDropWrapper(new CssLayout) {

  // TODO this style is set on the D&D wrapper, not the CssLayout!
  p.setStyleName(ExpressionList.StyleSeparator);
  p.setDropHandler(new DropHandler() {

    override def getAcceptCriterion(): AcceptCriterion = {
      // TODO only accept appropriate Little components
      return AcceptAll.get();
    }

    override def drop(event: DragAndDropEvent): Unit = {
      val droppedStep = getDroppedStep(event);
      val list = getStepList;
      if (list.contains(droppedStep)) {
        list.removeComponent(droppedStep);
      }
      val separator = ExpressionListSeparator.this;
      val separatorIndex = list.componentIndex(separator);
      list.add(droppedStep, separatorIndex + 1);
    }

    def getStepList: ExpressionList = {
      val t = ExpressionListSeparator.this
      val p = t.parent
      p.asInstanceOf[ExpressionList]
    }

    def getDroppedStep(event: DragAndDropEvent): Expression = {
      val sourceComponent = event.getTransferable().getSourceComponent().asInstanceOf[Component];
      if (sourceComponent.isInstanceOf[Expression]) {
        // An existing step is being moved from elsewhere
        return sourceComponent.asInstanceOf[Expression]
      } else if (sourceComponent.isInstanceOf[ToolboxItem]) {
        // A new step is being dropped from the toolbox
        val sourceStep = sourceComponent.asInstanceOf[ToolboxItem]
        return sourceStep.getStep
      } else {
        throw new ClassCastException("Drop not supported for "
          + sourceComponent.getClass().getSimpleName());
      }
    }

  });
}