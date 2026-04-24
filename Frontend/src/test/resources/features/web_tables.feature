Feature: Web Tables
  Scenario: Create 12 new records dynamically
    Given I open the web tables page
    When I add 12 new records
    Then 12 new records should be present
