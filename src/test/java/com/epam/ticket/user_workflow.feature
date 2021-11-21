Feature: Perform user operations
  As an User
  I want to perform real-life scenarios
  So it will be full workflow

  @CreateUser
  Scenario: Creating new user
    When I trying to create a new user with id 4 and name "Zhenya" and email "protas@mail.ru"
    Then The following information is received for the new user:
      | id | name   | email          |
      | 4  | Zhenya | protas@mail.ru |


  @CreateEvent
  Scenario: Creating new Event
    When I trying to create new event with id 4 and title "test_event" and date "10/10/2021 12:10"
    Then The following information is received for the new event:
      | id | title      | date             |
      | 4  | test_event | 10/10/2021 12:10 |

  @BookTicket
  Scenario: Booking the ticket
    Given User with ID 4 and event with ID 4
    When I trying to book the ticket with user ID 4 and event ID 4 and place number 22 and ticket category "PREMIUM"
    Then The following information is received for the ticket booked by this user with ID 4:
      | id | userId | eventId | place | category |
      | 4  | 4      | 4       | 22    | PREMIUM  |

  @CancelTicket
  Scenario: Canceling ticket
    Given Ticket with user ID 4 and event ID 4 and place number 22 and ticket category "PREMIUM"
    Then I cancel my ticket with ID 4