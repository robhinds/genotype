package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Genes.{BooleanGene, DoubleGene, IntGene}
import shapeless._

import scala.util.Random

trait Breeder[A] {
  def breed(a: A, b: A): A
}

object Breeder {

  def breed[A](a: A, b: A)(implicit breeder: Breeder[A]) = breeder.breed(a, b)

  implicit def intGeneBreeder = new Breeder[IntGene] {
    override def breed(a: IntGene, b: IntGene) = Random.shuffle(List(a, b)).head
  }

  implicit def doubleGeneBreeder = new Breeder[DoubleGene] {
    override def breed(a: DoubleGene, b: DoubleGene) = Random.shuffle(List(a, b)).head
  }

  implicit def booleanGeneBreeder = new Breeder[BooleanGene] {
    override def breed(a: BooleanGene, b: BooleanGene) = Random.shuffle(List(a, b)).head
  }

  implicit def hnilBreeder = new Breeder[HNil] {
    override def breed(a: HNil, b: HNil) = HNil
  }

  implicit def hconsBreeder[H, T <: HList](implicit headGen: Breeder[H], tailGen: Breeder[T]) =
    new Breeder[H :: T] {
      override def breed(a: H :: T, b: H :: T) = headGen.breed(a.head, b.head) :: tailGen.breed(a.tail, b.tail)
    }

  implicit def cnilBreeder: Breeder[CNil] =
    new Breeder[CNil] {
      override def breed(a: CNil, b: CNil): CNil = throw new RuntimeException("Invalid candidate configuration")
    }

  implicit def cconsBreeder[H, T <: Coproduct] =
    new Breeder[H :+: T] {
      override def breed(a: H :+: T, b: H :+: T) = throw new RuntimeException("Invalid candidate configuration")
    }

  implicit def genericToBreeder[T, L <: HList](implicit generic: Generic.Aux[T, L], lGen: Breeder[L]): Breeder[T] =
    new Breeder[T] {
      override def breed(a: T, b: T) = generic.from(lGen.breed(generic.to(a), generic.to(b)))
    }
}
