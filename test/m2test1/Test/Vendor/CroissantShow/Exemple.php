<?php

namespace CroissantShow\tests\units {

  use mageekguy\atoum;

  class Exemple extends atoum\test {

    public function testXY ( ) {
      $x = 7.0;
      $y = 42.0;

      $this
        ->if($coordinates = new \CroissantShow\Exemple($x, $y))
        ->then
          ->float($coordinates->getX())
            ->isEqualTo($x)
          ->float($coordinates->getY())
            ->isEqualTo($y)
        ;
    }
	    
    public function testXY2 ( ) {
      $x = 7.0;
      $y = 42.0;
      
      $coordinates = new \CroissantShow\Exemple($x, $y);
      
      $this
        ->float($coordinates->getX())
          ->isEqualTo($x)
        ->float($coordinates->getY())
          ->isEqualTo($y)
	      ;
    }
	
    public function testCast ( ) {
      $x = 7;
      $y = 42;
      
      $coordinates = new \CroissantShow\Exemple($x, $y);
      
      $this
        ->float($coordinates->getX())
          ->isEqualTo($x)
        ->float($coordinates->getY())
          ->isEqualTo($y)
	      ;
    }
	}
}
