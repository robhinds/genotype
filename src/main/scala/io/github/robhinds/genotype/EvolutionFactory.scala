package io.github.robhinds.genotype

import io.github.robhinds.genotype.Configuration.{EvolutionConfig, PopulationConfig}
import io.github.robhinds.genotype.evolution.{Breeder, Generator, Mutator}

import scala.annotation.tailrec
import scala.util.Random

case class EvolutionFactory[A](candidate: A,
  populationConfig: PopulationConfig,
  evolutionConfig: EvolutionConfig,
  errorRate: A => Double)
  (implicit gen: Generator[A], mut: Mutator[A], breeder: Breeder[A]) {

  def run = {
    @tailrec
    def evolutionCycle(pop: List[A], generation: Int): List[A] = {
      if (generation > evolutionConfig.generations) return pop
      val sorted = pop.map(c => (c, errorRate(c))).sortBy(_._2)
      println(s"Generation: $generation : Top performer: ${sorted.head._1} : ${sorted.head._2}")
      val nextGen = sorted.head._1 :: mutate(sorted) ::: evolve(sorted)
      evolutionCycle(nextGen, generation+1)
    }

    def topPerformers(s: List[(A, Double)]): List[A] = s.take(populationConfig.size/2).map(_._1)

    def mutate(sortedList: List[(A, Double)]): List[A] = topPerformers(sortedList).map(mut.mutate)

    def evolve(sortedList: List[(A, Double)]): List[A] =
      (0 to (populationConfig.size/2)).map { i: Int =>
        breeder.breed(
          Random.shuffle(topPerformers(sortedList)).head,
          Random.shuffle(topPerformers(sortedList)).head
        )
      }.toList

    val initialPop = generateCandidates()
    evolutionCycle(initialPop.toList, 0)
  }

  private def generateCandidates(popSize: Int = populationConfig.size) = {
    (0 to popSize).map(i => gen.generate(candidate))
  }
}
