package com.jitterted;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DrinkMachineRecipeCostTest {

  @Test
  public void smallCoffeeRecipeCosts75cents() throws Exception {
    Ingredient coffee = DrinkMachine.addAllIngredients().get(0);
    Recipe recipe = new Recipe(coffee);

    double cost = recipe.cost();

    assertThat(cost)
        .isCloseTo(0.75, Offset.offset(0.001));
  }

  @Test
  public void cafeLatteRecipeCostsWhateverItCosts() throws Exception {
    RecipeFactory recipeFactory = new RecipeFactory(DrinkMachine.addAllIngredients());
    Recipe latte = recipeFactory.create("Espresso", "Espresso", "Steamed Milk");

    double cost = latte.cost();

    assertThat(cost)
        .isCloseTo(2.55, Offset.offset(0.001));
  }
}