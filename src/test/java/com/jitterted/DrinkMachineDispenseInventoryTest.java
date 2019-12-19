package com.jitterted;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

class DrinkMachineDispenseInventoryTest {

  @Test
  public void coffeeIngredientInventoryIsReducedWhenDispensingCoffee() throws Exception {
    Ingredient coffeeIngredient = DrinkMachine.addAllIngredients().get(0);
    coffeeIngredient.setStock(10);
    Recipe coffeeRecipe = new Recipe(coffeeIngredient);

    coffeeRecipe.dispense();

    assertThat(coffeeIngredient.getStock())
        .isEqualTo(9);
  }

  @Test
  public void latteReducesEspressoInventoryByTwo() throws Exception {
    List<Ingredient> allIngredients = DrinkMachine.addAllIngredients();
    RecipeFactory recipeFactory = new RecipeFactory(allIngredients);
    Recipe latte = recipeFactory.create("Espresso", "Espresso", "Steamed Milk");
    Ingredient espresso = null;
    for (Ingredient ingredient : allIngredients) {
      if (ingredient.getName().equals(IngredientName.ESPRESSO)) {
        espresso = ingredient;
        break;
      }
    }
    assumeThat(espresso)
        .isNotNull();
    espresso.setStock(10);

    latte.dispense();

    assertThat(espresso.getStock())
        .isEqualTo(8);
  }

}