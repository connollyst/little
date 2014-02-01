package com.quane.little.web.view

import vaadin.scala.Component
import vaadin.scala.CssLayout
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.Measure
import vaadin.scala.Units
import vaadin.scala.VerticalLayout

object LittleFunctionDefinition {
  val STYLE = "l-function";
  val STYLE_BODY = STYLE + "-body";
  val STYLE_BODY_LEFT = STYLE + "-body-left";
  val STYLE_FOOT = STYLE + "-foot";
  val STYLE_HEAD = STYLE + "-head";
  val STYLE_HEAD_NAME_FIELD = STYLE_HEAD + "-name";
}

class LittleFunctionDefinition(name: String) extends VerticalLayout {

  spacing = false;
  addComponent(header());
  addComponent(body());
  addComponent(footer());

  def header(): Component = {
    new FunctionDefinitionHeader(name)
  }

  def body(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
//    val bodyInner = new LittleStepList
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = LittleFunctionDefinition.STYLE_BODY_LEFT
    body.addComponent(bodyLeft);
    // body.addComponent(bodyInner);
    body.styleName = LittleFunctionDefinition.STYLE_BODY;
    body.spacing = false;
    // initMockData(bodyInner);
    body;
  }

  def footer(): Component = {
    val footer = new CssLayout();
    footer.styleName = LittleFunctionDefinition.STYLE_FOOT;
    footer;
  }

}