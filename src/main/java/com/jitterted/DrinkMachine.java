package com.jitterted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jitterted.IngredientName.COCOA;
import static com.jitterted.IngredientName.COFFEE;
import static com.jitterted.IngredientName.CREAM;
import static com.jitterted.IngredientName.DECAF_COFFEE;
import static com.jitterted.IngredientName.ESPRESSO;
import static com.jitterted.IngredientName.FOAMED_MILK;
import static com.jitterted.IngredientName.STEAMED_MILK;
import static com.jitterted.IngredientName.SUGAR;
import static com.jitterted.IngredientName.WHIPPED_CREAM;

public class DrinkMachine {

  private static List<Drink> drinkList = new ArrayList<>();
  private static List<Ingredient> ingredientList = new ArrayList<>();

  public static void main(String[] args) {
    addAllIngredients();
    addAllDrinks();
    updateCosts();
    updateMakeable();
    display(System.out);
    startIO();
  }

  public static void startIO() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String input = "";

    while (true) {
      try {
        input = reader.readLine().toLowerCase();
        processInput(input);
      } catch (IOException e) {
        System.out.println("No idea why we got an IOException here." + e);
      }
    }
  }

  private static void processInput(String input) {
    if (input.isBlank()) {
      return;
    } else if (isQuitCommand(input)) {
      System.exit(0);
    } else if (isRestockCommand(input)) {
      restockIngredients();
      updateMakeable();
    } else if (isMenuChoice(input)) {
      makeDrink(drinkList.get(Integer.parseInt(input) - 1));
    } else {
      System.out.println("'" + input + "' was not valid. Choose from list above, or Q or R.");
    }
  }

  private static boolean isMenuChoice(String input) {
    try {
      int choiceNumber = Integer.parseInt(input);
      return choiceNumber > 0 && choiceNumber <= drinkList.size();
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static boolean isRestockCommand(String input) {
    return input.equals("r");
  }

  private static boolean isQuitCommand(String input) {
    return input.equals("q");
  }

  public static void display(PrintStream printStream) {
    printStream.println("Inventory:\n");
    for (Ingredient ingredient : ingredientList) {
      printStream.println(ingredient.getName().displayName() + ", " + ingredient.getStock());
    }

    printStream.println("Menu:\n");
    int count = 1;
    for (Drink d : drinkList) {
      printStream.printf("%d,%s,$%.2f," + d.getMakeable() + "\n\n", count, d.getName(), d.getCost());
      count++;
    }
  }

  public static void updateMakeable() {
    for (Drink drink : drinkList) {
      drink.updateDrinkState();
    }
  }

  public static void updateCosts() {
    for (Drink drink : drinkList) {
      double cost = drink.getRecipe().cost();
      drink.setCost(cost);
    }
  }

  public static void makeDrink(Drink drink) {
    if (drink.getMakeable()) {
      System.out.println("Dispensing: " + drink.getName() + "\n");
      drink.dispense();
    } else {
      System.out.println("Out of stock: " + drink.getName() + "\n");
    }
    updateMakeable();
    display(System.out);
  }

  public static void restockIngredients() {
    for (Ingredient ingredient : ingredientList) {
      ingredient.setStock(10);
    }
    updateMakeable();
    display(System.out);
  }

  public static List<Ingredient> addAllIngredients() {
    ingredientList.clear();
    ingredientList.add(new Ingredient(COFFEE, 0.75));
    ingredientList.add(new Ingredient(DECAF_COFFEE, 0.75));
    ingredientList.add(new Ingredient(SUGAR, 0.25));
    ingredientList.add(new Ingredient(CREAM, 0.25));
    ingredientList.add(new Ingredient(STEAMED_MILK, 0.35));
    ingredientList.add(new Ingredient(FOAMED_MILK, 0.35));
    ingredientList.add(new Ingredient(ESPRESSO, 1.10));
    ingredientList.add(new Ingredient(COCOA, 0.90));
    ingredientList.add(new Ingredient(WHIPPED_CREAM, 1.00));

    Collections.sort(ingredientList);

    return ingredientList;
  }

  public static void addAllDrinks() {
    RecipeFactory recipeFactory = new RecipeFactory(ingredientList);
    drinkList.add(new Drink("Coffee", recipeFactory.create("Coffee", "Coffee", "Coffee", "Sugar", "Cream")));
    drinkList.add(new Drink("Decaf Coffee", recipeFactory.create("Decaf Coffee", "Decaf Coffee", "Decaf Coffee", "Sugar", "Cream")));
    drinkList.add(new Drink("Caffe Latte", recipeFactory.create("Espresso", "Espresso", "Steamed Milk")));
    drinkList.add(new Drink("Caffe Americano", recipeFactory.create("Espresso", "Espresso", "Espresso")));
    drinkList.add(new Drink("Caffe Mocha", recipeFactory.create("Espresso", "Cocoa", "Steamed Milk", "Whipped Cream")));
    drinkList.add(new Drink("Cappuccino", recipeFactory.create("Espresso", "Espresso", "Steamed Milk", "Foamed Milk")));

    Collections.sort(drinkList);
  }

}