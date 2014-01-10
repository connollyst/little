package com.quane.little.game.view

import com.badlogic.gdx.graphics.{VertexAttribute, GL20, Mesh}
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import scala.collection.mutable.ListBuffer
import com.quane.little.game.entity.WorldEdge

/**
 *
 * @author Sean Connolly
 */
class MeshDrawer {

  private val whiteShader = createShader()
  private val allMeshes = new ListBuffer[Mesh]

  def begin() {
    whiteShader.begin()
  }

  def end() {
    whiteShader.end()
  }

  def dispose() {
    whiteShader.dispose()
    allMeshes.foreach(_.dispose())
  }

  def createMesh(isStatic: Boolean, vertices: Array[Float]): Mesh = {
    val mesh = new Mesh(isStatic, vertices.length, 0,
      new VertexAttribute(Usage.Position, 2, "a_position"))
    mesh.setVertices(vertices)
    allMeshes += mesh
    mesh
  }

  def createShader(): ShaderProgram = {
    // this shader tells opengl where to put things
    val vertexShader =
      "attribute vec4 a_position;    \n" +
        "void main()                   \n" +
        "{                             \n" +
        "   gl_Position = a_position;  \n" +
        "}                             \n"

    // this one tells it what goes in between the points (i.e
    // colour/texture)
    val fragmentShader =
      "#ifdef GL_ES                \n" +
        "precision mediump float;    \n" +
        "#endif                      \n" +
        "void main()                 \n" +
        "{                           \n" +
        "  gl_FragColor = vec4(1.0,1.0,1.0,0.2);	\n" +
        "}"

    // make an actual shader from our strings
    val meshShader = new ShaderProgram(vertexShader, fragmentShader)
    //    allShaders += meshShader

    // check there's no shader compile errors
    if (!meshShader.isCompiled)
      throw new IllegalStateException(meshShader.getLog)

    meshShader
  }

  def drawWall(wall: WorldEdge) {
    // TODO how to draw rectangles with triangles
    // http://openglbook.com/the-book/chapter-2-vertices-and-shapes/
    //    drawMesh(wall.mesh, whiteShader)
  }

  def drawMesh(mesh: Mesh, shader: ShaderProgram) {
    mesh.render(shader, GL20.GL_TRIANGLES)
  }


}
