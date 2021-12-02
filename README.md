
# Java Insurance Advisor API

API created using Java 11 and Spring Boot framework that illustrates a risk profile calculation. 

### Techs used
- Java 11;
- Spring Framework 2.6.1;
- Junit 5.8.1;
- Mockito 4.0.0;
- Lombok 1.18.22;

## Requirements

In order to run this project you have to install:

- [Java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html);
- [Maven](https://maven.apache.org/download.cgi);
- [Postman](https://www.postman.com/downloads/);

Once everything installed you can follow the next steps.

## How to run

```bash
git clone https://github.com/felipepossari/java-springboot-insurance-advisor.git
mvn clean test
mvn exec:java
```

## API Documentation

### Entities
* Customer Request:
```
{
  "age": 29,
  "dependents": 0,
  "house": {"ownership_status": "owned"},
  "income": 0,
  "marital_status": "married",
  "risk_questions": [1, 1, 1],
  "vehicle": {"year": 2005}
}
```


| Field | Value | Description |
|--|--|--|
| age | Integer >= 0 | |
| dependents | Integer >= 0 | |
| house | Object can null | Customer could or would not have a house |
| house.ownership_status | String `owned`/`mortgaged` | |
| income | Integer >= 0 | |
| marital_status | String `single`/`married` | |
| risk_questions | Integer Array `0`/`1` | Should contain 3 integers related with customer answers |
| vehicle | Object can null | Customer could or would not have a car |
| vehicle.year | Integer >= 0 | |

* Risk Profile Response:
```
{
    "auto": "regular",
    "disability": "ineligible",
    "home": "regular",
    "life": "regular"
}
```

| Field | Value |
|--|--|
| auto | `economic`/`responsible`/`regular`/`ineligible` |
| disability | `economic`/`responsible`/`regular`/`ineligible` |
| home | `economic`/`responsible`/`regular`/`ineligible` |
| life | `economic`/`responsible`/`regular`/`ineligible` |

* Error Response:
```
[
    {
        "code": "R000",
        "message": "Error reason"
    }
]
```

## API Document
### Calculate Risk
* **URL**: /risks
* **Method**: POST
* **Body**: Customer Request
* **Success Response**:
    * **Code**: 200 OK
    * **Content**: Risk Profile Response
* **Error Responses**:
    * **Code**: 400 Bad Request
    * **Content**: Error Response
* **Sample Call**:
```
curl --location --request POST 'http://localhost:8080/risks' \
--header 'Content-Type: application/json' \
--data-raw '{
  "age": 29,
  "dependents": 0,
  "house": {"ownership_status": "owned"},
  "income": 0,
  "marital_status": "married",
  "risk_questions": [1, 1, 1],
  "vehicle": {"year": 2005}
}'
```
Note: You can also run the collection `Risks API.postman_collection.json` located in folder `postman`. Just import it into Postman and run.

### Error Codes
|Error Code| Message  |  
|--|--|  
| R000  | Failure to parse request body |  
| R001  | Age number invalid |  
| R002  | Dependents number invalid |  
| R003 | House value invalid. It must be owned or mortgaged |  
| R004 | Income number invalid |  
| R005 | Marital status cannot be null or empty |  
| R006 | Marital status invalid. It must be single or married |
| R007 | Risk questions cannot be null or empty |
| R008 | Risk questions must have three answers |
| R009 | Risk questions values invalid. They must be zero or one |
| R010 | Vehicle year value invalid |
| R999  | An unknown error happened |


## Project Folder Structure
```
.
├── postman                               # Folder with holds the collection
└── src                                   # Source code
    ├── main
    │     ├── java/com/felipepossari
    │     │     └── insuranceadvisor
    │     │     ├── adapter               # Classes responsible for receive input data from outside world like REST API
    │     │     └── application           # System core. Contains the business logic
    │     └── resources
    └── test                              # Test code classes
        └── java/com/felipepossari
            └── insuranceadvisor
                ├── base                  # Classes used by the tests. E.g.: Test builders and default constants
                ├── integration           # Integration test classes
                └── unit                  # Unit test classes

```

The project was built thinking about clean architecture. The business logic of the system are isolated in the `application` folder and are accessible through an interface.

```
.
└── insuranceadvisor
    ├── adapter
    │         └── in
    │             └── web
    └── application
        ├── domain
        ├── helper
        ├── port
        └── service
```

The `adapter/in` folder has the entrance for the outside world. Inside this folder are the Spring controllers and the request and response classes that allows any other system using HTTP requests. This project has no external integration but case we would need to save the risk profile any database, the integration would be created inside a folder called `out`.

The `domain` folder has the classes related with the business like customer, rules and insurance. 

The `helper` folder has an interface to isolate the code that gets the current date time and a factory that returns a list of rules. I chose this approach to avoid static methods in a rule class. This way the class became testable. I isolated the rule creation inside a factory class because it is not responsibility of the use case knows how to create the rules list to be applied in the engine. Once with the rules creation isolated, the use case needs only to get the list of rules and apply then.

The `port` folder has the interfaces used to communicate with external layers. The input interfaces stays in the `in` folder and act as use cases. The only was the adapter classes interact with the application is through the interfaces of this folder. In case we need save data in any database or call another service with HTTP new interfaces would be created into folder `out`. There interfaces were the application send any kind of data are called ports.

The `service` folder has the implementation classes of the use case interfaces. Together with the domain classes the business logic are written.

## Calculate Risk Profile Use Case

The risk profile calculation is executed in the class `CalculateRiskProfileUseCaseService.java`. It receives a customer, fill the insurances with the customer's base score, get the rules from factory and apply them.
```java
public EnumMap<InsuranceType, Insurance> calculate(Customer customer) {
    var insurances = fillInsurancesScore(customer);
    applyRules(customer, insurances, ruleFactory.getRules());
    return insurances;
}
```

In case a new insurance needs to be considered in the business, we just need to add it into `InsuranceType` enum and add in the `RiskProfileApiResponse` class in the `adapter` layer. I chose to create the RiskProfileApiResponse class with fixed fields to improve readability but it could be created as a Map<String, String>. With it the respose would be dynamic.
```java
public enum InsuranceType {
    LIFE, DISABILITY, HOME, AUTO
}
```
In case we need to add new rules, we need to create a new domain class rule, implement the interface Rule and add it into the RulesFactory


## License
[MIT](https://choosealicense.com/licenses/mit/)
