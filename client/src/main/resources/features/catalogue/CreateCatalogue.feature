Feature: Create a feature catalogue
  An automated functional testing process
  that uses gherkin language to define application behavior
  has to be able to record information about a catalogue of features
  so that the application features can be added to the catalogue
  and interested people can read them.

  Scenario: Create a feature catalogue
    Given the information for a feature catalogue is known
    When the creation of the catalogue is requested
    Then the catalogue information is recorded
