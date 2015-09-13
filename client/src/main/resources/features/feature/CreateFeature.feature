Feature: Create a feature.
  An automated functional testing process
  that uses gherkin language to define application behavior
  has to be able to record information about a specific feature (user story)
  so that a set of implementation scenarios can be recorded against the feature
  and interested people can read them.

  Scenario: Create a feature
    Given there is a feature catalogue
    Given there is a feature set
    Given the information for a feature is known
    When the creation of the feature is requested
    Then the feature information is recorded