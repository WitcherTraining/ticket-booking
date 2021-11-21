Feature: Update user profile
  As an User
  I want to update my profile
  So it will be with new data

  @UpdateUserName
  Scenario: Updating name of user
    When I trying to change my name to "Zhenya"
    Then I see updated user profile with new name "Zhenya"