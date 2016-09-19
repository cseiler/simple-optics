package de.c.seiler.simpleoptics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Optional;

import org.junit.Test;

import de.c.seiler.simpleoptics.Lens;
import de.c.seiler.simpleoptics.OptionalLens;
import de.c.seiler.simpleoptics.View;
import de.c.seiler.testdomain.Car;
import de.c.seiler.testdomain.CarMaker;
import de.c.seiler.testdomain.Material;
import de.c.seiler.testdomain.Seats;

public class OpticsTest
{

  @Test
  public void test()
  {
    Car car = buildCar(Color.GRAY);
    Lens<Car, Color> Col = new Lens<>(Car::getColor, (c, col) -> {c.setColor(col); return c;});
    Color col = Col.get(car);
    assertEquals(Color.GRAY, col);
    
    Lens<Car,Seats> Seats = new Lens<>(Car::getSeats, (c, s) -> {c.setSeats(s); return c;});
    View<Seats,Material> Mat = new View<Seats,Material>(s -> s.getMaterial());
    View<Car, Material> SeatMaterial = Seats.andThen(Mat);
    Material m = SeatMaterial.get(car);
    assertEquals(Material.LEATHER, m);
    
    OptionalLens<Car, Color> oCol = new OptionalLens<>(new Lens<>(Car::getColor, (c, cl) -> {c.setColor(cl); return c;}));
    Optional<Color> ocol = oCol.get(car);
    assertTrue(ocol.isPresent());
    assertEquals(Color.GRAY, ocol.get());
    
  }

  private Car buildCar(Color color)
  {
    return new Car(CarMaker.AlfaRomeo, color, 2004, new Seats(Material.LEATHER, Color.RED));
  }

}
