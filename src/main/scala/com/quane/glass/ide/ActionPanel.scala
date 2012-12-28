package com.quane.glass.ide

import scala.io.Source
import scala.swing._
import javax.swing.BorderFactory
import java.awt.Color

class ActionPanel(val title: String)
        extends BoxPanel(Orientation.Horizontal) {

    val source = getClass.getResource("/img/circle/white.png")
    val icon = new ImageIcon(source)

    border = BorderFactory.createLineBorder(Color.black)

    contents += new LabelIcon(title, icon)
}

class SetterPanel(title: String)
        extends ActionPanel(title) {
    
    contents += new Label("=")
    contents += new TextField
        
}