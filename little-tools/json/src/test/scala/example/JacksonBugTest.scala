package example

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping._
import com.fasterxml.jackson.databind.ObjectMapper._
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
import com.fasterxml.jackson.databind.{JavaType, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

class Bar

trait Baz

class Foo {
  val list = List[String]()
}

class FooBar {
  val list = List[Bar]()
}

class FooBaz {
  val list = List[Baz]()
}

@RunWith(classOf[JUnitRunner])
class JacksonBugTest extends FlatSpec with ShouldMatchers {

  val alwaysUse = true
  val neverUse = false

  "jackson" should "serialize String with basic mapper" in {
    serialize("foo", basicMapper)
  }
  it should "serialize String with mapper with default typing enabled" in {
    serialize("foo", defaultTypeMapper)
  }
  it should "serialize String with mapper with custom 'java lang object' typing" in {
    serialize("foo", customTypeMapper(JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'java lang object' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'java lang object' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'java lang object' typing (disabled)" in {
    serialize("foo", customTypeMapperDisabled(JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing" in {
    serialize("foo", customTypeMapper(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize("foo", customTypeMapperDisabled(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing" in {
    serialize("foo", customTypeMapper(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing (disabled)" in {
    serialize("foo", customTypeMapperDisabled(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-final' typing" in {
    serialize("foo", customTypeMapper(NON_FINAL))
  }
  it should "serialize String with mapper with custom 'non-final' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, NON_FINAL))
  }
  it should "serialize String with mapper with custom 'non-final' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, NON_FINAL))
  }
  it should "serialize String with mapper with custom 'non-final' typing (disabled)" in {
    serialize("foo", customTypeMapperDisabled(NON_FINAL))
  }
  it should "serialize List[String] with basic mapper" in {
    serialize(List[String]("foo"), basicMapper)
  }
  it should "serialize List[String] with mapper with default typing enabled" in {
    serialize(List[String]("foo"), defaultTypeMapper)
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing" in {
    serialize(List[String]("foo"), customTypeMapper(JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing" in {
    serialize(List[String]("foo"), customTypeMapper(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(List[String]("foo"), customTypeMapper(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing (disabled)" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing" in {
    serialize(List[String]("foo"), customTypeMapper(NON_FINAL))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, NON_FINAL))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, NON_FINAL))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing (disabled)" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(NON_FINAL))
  }
  it should "serialize Foo with basic mapper" in {
    serialize(new Foo, basicMapper)
  }
  it should "serialize Foo with mapper with default typing enabled" in {
    serialize(new Foo, defaultTypeMapper)
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing" in {
    serialize(new Foo, customTypeMapper(JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(new Foo, customTypeMapperDisabled(JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing" in {
    serialize(new Foo, customTypeMapper(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(new Foo, customTypeMapperDisabled(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(new Foo, customTypeMapper(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing (disabled)" in {
    serialize(new Foo, customTypeMapperDisabled(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing" in {
    serialize(new Foo, customTypeMapper(NON_FINAL))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, NON_FINAL))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, NON_FINAL))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing (disabled)" in {
    serialize(new Foo, customTypeMapperDisabled(NON_FINAL))
  }
  it should "serialize FooBar with basic mapper" in {
    serialize(new FooBar, basicMapper)
  }
  it should "serialize FooBar with mapper with default typing enabled" in {
    serialize(new FooBar, defaultTypeMapper)
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing" in {
    serialize(new FooBar, customTypeMapper(JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(new FooBar, customTypeMapperDisabled(JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing" in {
    serialize(new FooBar, customTypeMapper(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(new FooBar, customTypeMapperDisabled(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(new FooBar, customTypeMapper(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing (disabled)" in {
    serialize(new FooBar, customTypeMapperDisabled(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing" in {
    serialize(new FooBar, customTypeMapper(NON_FINAL))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, NON_FINAL))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, NON_FINAL))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing (disabled)" in {
    serialize(new FooBar, customTypeMapperDisabled(NON_FINAL))
  }
  it should "serialize FooBaz with basic mapper" in {
    serialize(new FooBaz, basicMapper)
  }
  it should "serialize FooBaz with mapper with default typing enabled" in {
    serialize(new FooBaz, defaultTypeMapper)
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing" in {
    serialize(new FooBaz, customTypeMapper(JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(new FooBaz, customTypeMapperDisabled(JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing" in {
    serialize(new FooBaz, customTypeMapper(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(new FooBaz, customTypeMapperDisabled(OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(new FooBaz, customTypeMapper(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing (disabled)" in {
    serialize(new FooBaz, customTypeMapperDisabled(NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing" in {
    serialize(new FooBaz, customTypeMapper(NON_FINAL))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, NON_FINAL))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, NON_FINAL))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing (disabled)" in {
    serialize(new FooBaz, customTypeMapperDisabled(NON_FINAL))
  }

  private def serialize(value: Any, mapper: ObjectMapper) = {
    mapper.writeValueAsString(value)
  }

  private def basicMapper: ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.registerModule(new DefaultScalaModule)
    mapper
  }

  private def defaultTypeMapper: ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.registerModule(new DefaultScalaModule)
    mapper.enableDefaultTyping()
    mapper
  }

  private def customTypeMapper(typing: DefaultTyping): ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.setDefaultTyping(customTypeResolver(typing))
    mapper.registerModule(new DefaultScalaModule)
    mapper
  }

  private def customTypeMapperDisabled(typing: DefaultTyping): ObjectMapper = {
    val mapper = customTypeMapper(typing)
    mapper.disableDefaultTyping()
    mapper
  }


  private def customTypeResolver(typing: DefaultTyping): TypeResolverBuilder[_] = {
    var typer: TypeResolverBuilder[_] = new DefaultTypeResolverBuilder(typing)
    typer = typer.init(JsonTypeInfo.Id.CLASS, null).asInstanceOf[TypeResolverBuilder[_]]
    typer = typer.inclusion(JsonTypeInfo.As.WRAPPER_ARRAY).asInstanceOf[TypeResolverBuilder[_]]
    typer
  }

  private def customTypeMapper(doUseForType: Boolean, typing: DefaultTyping): ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.setDefaultTyping(customTypeResolver(doUseForType, typing))
    mapper.registerModule(new DefaultScalaModule)
    mapper
  }

  private def customTypeResolver(doUseForType: Boolean, typing: DefaultTyping): TypeResolverBuilder[_] = {
    var typer: TypeResolverBuilder[_] = new DefaultTypeResolverBuilder(typing) {
      override def useForType(t: JavaType): Boolean = doUseForType
    }
    typer = typer.init(JsonTypeInfo.Id.CLASS, null).asInstanceOf[TypeResolverBuilder[_]]
    typer = typer.inclusion(JsonTypeInfo.As.WRAPPER_ARRAY).asInstanceOf[TypeResolverBuilder[_]]
    typer
  }

}