package com.quane.little.ide.language

import scala.swing.Panel
import scala.swing.TextField
import scala.swing.FlowPanel

class PointerPanel
        extends FlowPanel {

    private val field = new TextField("", 16)

    contents += field
    
    def variableName = field.text

}