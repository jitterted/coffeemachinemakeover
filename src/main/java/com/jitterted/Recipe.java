package com.jitterted;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class Recipe {
  private Set<RecipeIngredient> recipeIngredientSet;

  public Recipe(Ingredient... ingredients) {
    recipeIngredientSet = Stream.of(ingredients)
                                .collect(
                                    groupingBy(identity(), counting())
                                )
                                .entrySet()
                                .stream()
                                .map(entry ->
                                         new RecipeIngredient(
                                             entry.getKey(),
                                             Math.toIntExact(entry.getValue())))
                                .collect(toSet());
  }

  public void dispense() {
    for (RecipeIngredient recipeIngredient : recipeIngredientSet) {
      recipeIngredient.reduceInventory();
    }
  }

  public double cost() {
    double cost = 0;
    for (RecipeIngredient recipeIngredient : recipeIngredientSet) {
      cost += recipeIngredient.cost();
    }
    return cost;
  }

  boolean isMakeable() {
    for (RecipeIngredient recipeIngredient : recipeIngredientSet) {
      if (recipeIngredient.hasSufficientStock()) {
        return false;
      }
    }
    return true;
  }

  private static class RecipeIngredient {
    private final Ingredient ingredient;
    private final int quantity;

    private RecipeIngredient(Ingredient ingredient, int quantity) {
      this.ingredient = ingredient;
      this.quantity = quantity;
    }

    public void reduceInventory() {
      ingredient.reduceInventoryBy(quantity);
    }

    public double cost() {
      return ingredient.getCost() * quantity;
    }

    public boolean hasSufficientStock() {
      return ingredient.hasSufficientStockFor(quantity);
    }
  }
}
