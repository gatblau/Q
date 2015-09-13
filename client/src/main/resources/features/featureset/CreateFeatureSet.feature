Feature: Create a feature set.
  A feature set is a group of related features.

  Scenario: Create a feature set
    Given there is a feature catalogue
    Given the information for a feature set is known
    When the creation of the feature set is requested
    Then the feature set information is recorded