Feature: Find all available catalogues
  A member of the development team
  needs to retrieve a list of all recorded catalogues containing features
  in order to inspect the content of those catalogues when required.

  Scenario: Get a list containing all recorded catalogues
    Given there are a few feature catalogues in the system
    When the list of available catalogues is requested
    Then the list of available catalogues is produced
    Then the list contains all recorded catalogues in the system
