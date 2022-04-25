Feature: Checkout overview

  Scenario: Navigate to overview page
    Given we open cartEmpty page using url https://www.zooplus.com/checkout/cartEmpty.htm
    And I handle the consent layer by clicking accept cookies button
    And I add a product from the recommendation section
    Then I should see overview page to be loaded
    And the url should contain {overview}
    And the product added in previous step should be available in the cart table

  Scenario: All necessary sections are present in overview page
    Given we are on the overview page
    Then we should see the cart table containing at least one product row
    And we should see "You might also like" section with product recommendation
    And we should see "Other recommended products" section with product recommendation
    And we should see price overview section containing the total calculation
    And we should see Proceed to Checkout button in price overview section
    And we should see methods of payment section

  Scenario: Cart total is less than Minimum order value
    Given we are on the overview page with one product with price less than 19.00 euros
    Then we should see an error message "Please note: the minimum order value is €19.00 (without shipping costs)" above the cart table
    And we should see the same error message "Please note: the minimum order value is €19.00 (without shipping costs)" above the proceed to checkout button in price overview section
    And proceed to checkout button should be greyed out and not actionable

  Scenario: Transition from cart value of less than minimum order value to more minimum order value
    Given we are on the overview page with one product with price less than 19.00 euros
    When we add a product worth more than 19 euros to the cart from recommendation
    Then the error message "Please note: the minimum order value is €19.00 (without shipping costs)" should disappear from cart section
    And the same error message "Please note: the minimum order value is €19.00 (without shipping costs)" should disappear from price overview section
    And proceed to checkout button should show in orange colour and should be actionable

  Scenario: Delete item from cart with delete button
    Given we are on the overview page with more than one product added to the cart
    When we click on the delete icon next to the price of the product
    Then the product should be removed from the cart table
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario: Delete item from cart with minus button when item count is 1
    Given we are on the overview page with more than one product added to the cart with quantity as 1
    When we click on the minus button for a produc
    Then the product should be removed from the cart table
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario: Delete item from cart with minus button by updating count text field when item count is more than 1
    Given we are on the overview page with more than one product added to the cart with quantity more than 1
    When we update the quantity text field between plus and minus buttons with 0
    And we click on the minus button for the product
    Then the product should be removed from the cart table
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario: Increase quantity of a product by default value using plus button
    Given we are on the overview page with at least one product in the cart table
    When we click the plus button
    Then the quantity should increase by 1 from the previous value
    And price in the last column of the product row should be updated to (item price * updated quantity)
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario: Increase quantity of a product by specific value using plus button
    Given we are on the overview page with at least one product in the cart table with quantity set to 1
    When we change the quantity in the text box between the plus and minus button to any number more than 1
    And we click the plus button
    Then the quantity should increase by the new number from the previous value of 1
    And price in the last column of the product row should be updated to (item price * updated quantity)
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario: Decrease quantity of a product by default value using minus button
    Given we are on the overview page with at least one product in the cart table with quantity more than 1
    When we click the minus button
    Then the quantity should decrease by 1 from the previous value
    And price in the last column of the product row should be updated to (item price * updated quantity)
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario: Decrease quantity of a product by specific value using minus button
    Given we are on the overview page with at least one product in the cart table with quantity set to more than 1
    When we change the quantity in the text box between the plus and minus button to any number more than 1 less than the quantity
    And we click the minus button
    Then the quantity should decrease to 1
    And price in the last column of the product row should be updated to (item price * updated quantity)
    And the subtotal price should be updated in the price overview section
    And the total price should be updated in the price overview section

  Scenario Outline: Update shipping country
    Given we are on the overview page with at lease one product in the cart table and cart value less than 49 euros
    When we click on the country name shown on the price overview section
    And we select {<country>} from the country dropdown in the popup
    And we enter {<postalCode>} in the Postcode field
    And we click Update button
    Then we should see shipping fee updated in the price overview section
    And we should see the total value updated the price overview section
    Examples:
      | country  | postalCode |
      | Germany  | 96050      |
      | Portugal | 5000       |

  Scenario: Enter valid discount coupon
    Given we are on the overview page with at least one product
    When we click "enter a coupon code" link in the price overview section
    And enter a valid coupon code
    And click the plus icon
    Then we should see a success message of coupon being applied
    And we should see a negative deduction of the coupon value
    And the total value should be updated

  Scenario: Enter invalid discount coupon
    Given we are on the overview page with at least one product
    When we click "enter a coupon code" link in the price overview section
    And enter an invalid coupon code
    And click the plus icon
    Then we should see an error message of coupon being applied
    And the total value should stay as before

  Scenario: Validate payment method
    Given we are on the overview page with at least one product
    When we check the payment section
    Then bank transfer should be listed under payment section

  Scenario: You might also like list traversal
    Given we are on the overview page with at lease one product
    When we click the right arrow on "You might also like" section
    Then we should be able to traverse right
    When we click the left arrow on "You might also like" section
    Then we should be able to traverse left

  Scenario: Add product from You might also like section
    Given we are on the overview page with at lease one product
    When we click on the cart button of a product in the "You might also like" section
    Then cart table should be updated with another row with the new product

  Scenario: Other recommended products list traversal
    Given we are on the overview page with at lease one product
    When we click the right arrow on "Other recommended products" section
    Then we should be able to traverse right
    When we click the left arrow on "Other recommended products" section
    Then we should be able to traverse left

  Scenario: Add product from Other recommended products section
    Given we are on the overview page with at lease one product
    When we click on the cart button of a product in the "Other recommended products" section
    Then cart table should be updated with another row with the new product