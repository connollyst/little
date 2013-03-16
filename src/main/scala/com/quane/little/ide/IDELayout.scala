package com.quane.little.ide

import java.awt.Component
import java.awt.Dimension

object IDELayout {

    val LineHeight = 40
    val LineWidth = Integer.MAX_VALUE
    val LineDimensions = new Dimension(IDELayout.LineWidth, IDELayout.LineHeight)

    val AlignLeft = Component.LEFT_ALIGNMENT
    val AlignRight = Component.RIGHT_ALIGNMENT
    val AlignCenter = Component.CENTER_ALIGNMENT
    val AlignBottom = Component.BOTTOM_ALIGNMENT
    val AlignTop = Component.TOP_ALIGNMENT
}