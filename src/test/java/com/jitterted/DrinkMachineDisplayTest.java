package com.jitterted;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class DrinkMachineDisplayTest {

  @Test
  public void displayPrintsIngredientsDisplayName() throws Exception {
    DrinkMachine.addAllIngredients();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    DrinkMachine.display(printStream);

    assertThat(baos.toString())
        .contains("Coffee", "Sugar", "Steamed Milk", "Whipped Cream");
  }
}