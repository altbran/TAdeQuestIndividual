package main

object Stat {
  
  def apply (hp: Int = 1, fuerza: Int = 1, velocidad: Int = 1, inteligencia: Int = 1) : Stat = {
    new Stat(1.max(hp), 1.max(fuerza), 1.max(velocidad), 1.max(inteligencia))
  }
}

case class Stat private(hp: Int = 1, fuerza: Int = 1, velocidad: Int = 1, inteligencia: Int = 1) {  //obligo a usar el companion
  //para instanciar, tienen que llamar al companion, no se usa mas 'new' --->  Stat(1,2,3,4)
  
  def copy(hp: Int = this.hp, fuerza: Int = this.fuerza, velocidad: Int = this.velocidad, inteligencia: Int = this.inteligencia) : Stat = {
    Stat(hp,fuerza,velocidad,inteligencia)
  }
      
      
  def statPrincipal = atributos.max
  
  private def atributos = List(hp,fuerza,velocidad,inteligencia)
}