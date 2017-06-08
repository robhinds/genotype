package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Genes.{BooleanGene, DoubleGene, IntGene}
import shapeless.{HList, HNil, _}

import scala.util.Random

trait Generator[A] {
  def generate(a: A): A
}

object Generator {

  def generate[A](a: A)(implicit gen: Generator[A]) = gen.generate(a)

  implicit def intGenerator = new Generator[IntGene] {
    override def generate(a: IntGene) = a.copy(value = Some(Random.nextInt(a.max - a.min) + a.min))
  }

  implicit def doubleGenerator = new Generator[DoubleGene] {
    override def generate(a: DoubleGene) = a.copy(value = Some((Random.nextDouble * (a.max - a.min)) + a.min))
  }

  implicit def booleanGenerator = new Generator[BooleanGene] {
    override def generate(a: BooleanGene) = a.copy(value = Some(Random.nextBoolean))
  }

  implicit def hnilGenerator = new Generator[HNil] {
    override def generate(a: HNil) = HNil
  }

  implicit def hconsGenerator[H, T <: HList](implicit headGen: Generator[H], tailGen: Generator[T]) =
    new Generator[H :: T] {
      override def generate(a: H :: T) = headGen.generate(a.head) :: tailGen.generate(a.tail)
    }

  implicit def cnilGenerator: Generator[CNil] =
    new Generator[CNil] {
      override def generate(a: CNil): CNil = throw new RuntimeException("Invalid candidate configuration")
    }

  implicit def cconsGenerator[H, T <: Coproduct] =
    new Generator[H :+: T] {
      override def generate(a: H :+: T) = throw new RuntimeException("Invalid candidate configuration")
    }

  implicit def genericToGenerator[T, L <: HList](implicit generic: Generic.Aux[T, L], lGen: Generator[L]): Generator[T] =
    new Generator[T] {
      override def generate(a: T) = generic.from(lGen.generate(generic.to(a)))
    }
}
