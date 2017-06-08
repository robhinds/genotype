package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Configuration.LearningRate
import io.github.robhinds.genotype.Genes.{BooleanGene, DoubleGene, IntGene}
import shapeless._

import scala.util.Random

trait Mutator[A] {
  def mutate(a: A): A
}

object Mutator {

  def mutate[A](a: A)(implicit m: Mutator[A], lr: LearningRate) = m.mutate(a)

  def gaussianProbability() = {
    val standardDeviation = 0.4
    val mean = 1.0
    math.abs(Random.nextGaussian() * standardDeviation + mean)
  }

  implicit def intMutator = new Mutator[IntGene] {
    override def mutate(a: IntGene) =
      a.copy(value = a.value.map(i => (i * gaussianProbability).toInt))
  }

  implicit def doubleMutator = new Mutator[DoubleGene] {
    override def mutate(a: DoubleGene) = a.copy(value = a.value.map(_ * gaussianProbability))
  }

  implicit def booleanMutator = new Mutator[BooleanGene] {
    override def mutate(a: BooleanGene) = a.copy(value = Some(Random.nextBoolean))
  }

  implicit def hnilMutator = new Mutator[HNil] {
    override def mutate(a: HNil) = HNil
  }

  implicit def hconsMutator[H, T <: HList](implicit headGen: Mutator[H], tailGen: Mutator[T]) =
    new Mutator[H :: T] {
      override def mutate(a: H :: T) = headGen.mutate(a.head) :: tailGen.mutate(a.tail)
    }

  implicit def cnilMutator: Mutator[CNil] =
    new Mutator[CNil] {
      override def mutate(a: CNil): CNil = throw new RuntimeException("Invalid candidate configuration")
    }

  implicit def cconsMutator[H, T <: Coproduct] =
    new Mutator[H :+: T] {
      override def mutate(a: H :+: T) = throw new RuntimeException("Invalid candidate configuration")
    }

  implicit def genericToMutator[T, L <: HList](implicit generic: Generic.Aux[T, L], lGen: Mutator[L]): Mutator[T] =
    new Mutator[T] {
      override def mutate(a: T) = generic.from(lGen.mutate(generic.to(a)))
    }
}
