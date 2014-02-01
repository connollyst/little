package com.quane.little.web.view

import vaadin.scala.Component
import vaadin.scala.CssLayout
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.Measure
import vaadin.scala.Units
import vaadin.scala.VerticalLayout

object FunctionDefinition {
  val Style = "l-function";
  val StyleBody = Style + "-body";
  val StyleHeadLeft = StyleBody + "-left";
  val StyleFoot = Style + "-foot";
  val StyleHead = Style + "-head";
  val StyleHeadNameField = StyleHead + "-name";
}

class FunctionDefinition(name: String) extends VerticalLayout {

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
    bodyLeft.styleName = FunctionDefinition.StyleHeadLeft
    body.addComponent(bodyLeft);
    // body.addComponent(bodyInner);
    body.styleName = FunctionDefinition.StyleBody;
    body.spacing = false;
    // initMockData(bodyInner);
    body;
  }

  def footer(): Component = {
    val footer = new CssLayout();
    footer.styleName = FunctionDefinition.StyleFoot;
    footer;
  }

}