@WebReport
Feature: Ipl_Navigation
  

  Scenario Outline: LoginToWebsite
    Given I want to launch application for "<component>" in "<locale>"
    Then I verify the page title as "<title>"
		
		@ipl
    Examples: 
      | component        | locale | title                                  |
      | component1       | in     | Indian Premier League Official Website |