package io.github.robhinds.genotype

import io.github.robhinds.genotype.Configuration.{EvolutionConfig, PopulationConfig}
import shapeless._
import Generator.generate

import scala.annotation.tailrec

case class EvolutionFactory[A: Generic](c: A, pc: PopulationConfig, ec: EvolutionConfig) {

  def run = {
    @tailrec
    def evolutionCycle(pop: List[A], generation: Int): List[A] = {
      if (generation > ec.generations) return pop

      //1. rank
      //2. evolve
      evolutionCycle(pop, generation+1)
    }

    val initialPop = generateCandidates()
    evolutionCycle(initialPop.toList, 0)
  }

  private def generateCandidates(popSize: Int = pc.size) = {
    (0 to popSize) map { i =>
      val hl = Generic[A].to(c)
      //hl
      //val rando = generate(hl)
      //Generic[A].from(rando)
    }
    List[A]()
  }
}
